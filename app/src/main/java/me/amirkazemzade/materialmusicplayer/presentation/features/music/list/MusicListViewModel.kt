package me.amirkazemzade.materialmusicplayer.presentation.features.music.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.domain.model.SortType
import me.amirkazemzade.materialmusicplayer.domain.model.StatusCore
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMediaControllerUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase
import me.amirkazemzade.materialmusicplayer.presentation.common.extensions.reorder

@OptIn(ExperimentalCoroutinesApi::class)
class MusicListViewModel(
    private val getMusicListUseCase: GetMusicListUseCase,
    getMediaControllerUseCase: GetMediaControllerUseCase,
) : ViewModel() {
    val mediaControllerState = getMediaControllerUseCase()

    private val _refresh = MutableStateFlow(false)
    private val _sortType = MutableStateFlow(SortType.DATE_ADDED)
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
                index = event.index,
                mediaController = event.mediaController
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
        mediaController: MediaController,
    ) {
        if (state.value !is MusicListState.Success) return
        val playList = (state.value as MusicListState.Success).musics
            .reorder(index)
            .map { musicFile -> createMediaItem(musicFile) }

        playList.let {
            mediaController.clearMediaItems()
            mediaController.setMediaItems(playList)
            mediaController.prepare()
            mediaController.play()
        }
    }

    override fun onCleared() {
        mediaControllerState.value.data?.release()
        super.onCleared()
    }
}

private fun createMediaItem(musicFile: MusicFile) =
    MediaItem.Builder()
        .setUri(musicFile.uri)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setTitle(musicFile.title)
                .setArtist(musicFile.artist)
                .build()
        )
        .build()
