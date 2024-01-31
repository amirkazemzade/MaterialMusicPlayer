package me.amirkazemzade.materialmusicplayer.presentation.features.music.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import me.amirkazemzade.materialmusicplayer.domain.model.DefaultSortType
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.domain.model.StatusCore
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicPlayerControllerUseCase
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.events.MusicListEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.states.MusicListState

@OptIn(ExperimentalCoroutinesApi::class)
class MusicListViewModel(
    private val getMusicListUseCase: GetMusicListUseCase,
    getMusicPlayerControllerUseCase: GetMusicPlayerControllerUseCase,
) : ViewModel() {
    val musicPlayerControllerState = getMusicPlayerControllerUseCase(viewModelScope)
    private val musicPlayerController
        get() = musicPlayerControllerState.value.data

    private val _refresh = MutableStateFlow(false)
    private val _sortType = MutableStateFlow(DefaultSortType.DATE_ADDED)
    private val _sortOrder = MutableStateFlow(SortOrder.DESC)
    private val _musicListState =
        combine(_refresh, _sortType, _sortOrder) { _, sortType, sortOrder ->
            getMusicListUseCase(sortType, sortOrder)
        }
            .flatMapLatest { it }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), StatusCore.Loading())

    private val _state = MutableStateFlow<MusicListState>(MusicListState.Loading())
    val state = combine(
        _sortType,
        _sortOrder,
        _musicListState
    ) { sortType, sortOrder, status ->
        val currentState = when (status) {
            is StatusCore.Loading -> {
                val state = _state.value
                if (state is MusicListState.Success)
                    MusicListState.Success(
                        musics = state.musics,
                        partialLoading = true,
                        sortType = sortType,
                        sortOrder = sortOrder,
                    )
                else {
                    MusicListState.Loading(
                        sortType = sortType,
                        sortOrder = sortOrder,
                    )
                }
            }

            is StatusCore.Error -> MusicListState.Error(
                sortType = sortType,
                sortOrder = sortOrder,
                message = status.message,
            )

            is StatusCore.Success -> MusicListState.Success(
                sortType = sortType,
                sortOrder = sortOrder,
                musics = status.data,
                errorCount = status.partialMessage ?: 0,
            )
        }
        _state.value = currentState
        currentState
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MusicListState.Loading())

    fun onEvent(event: MusicListEvent) {
        when (event) {
            is MusicListEvent.Retry -> {
                _refresh.value = true
            }

            is MusicListEvent.Play -> playMusic(
                index = event.index
            )

            is MusicListEvent.SortByOrder -> {
                _sortOrder.value = event.sortOrder
            }

            is MusicListEvent.SortByType -> {
                _sortType.value = event.sortType
            }

            is MusicListEvent.Search -> TODO("Add search")
        }
    }

    private fun playMusic(
        index: Int,
    ) {
        val musicListState = state.value
        if (musicListState !is MusicListState.Success) return
        musicPlayerController?.play(
            musics = musicListState.musics,
            startIndex = index,
        )
    }

    override fun onCleared() {
        musicPlayerController?.release()
        super.onCleared()
    }
}