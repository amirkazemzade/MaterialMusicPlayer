package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import me.amirkazemzade.materialmusicplayer.data.MusicPlayerController
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicPlayerControllerUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.GetQueueListAsFlowUseCase
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.events.MusicQueueListEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.states.MusicQueueListState

class MusicQueueListViewModel(
    getMusicPlayerControllerUseCase: GetMusicPlayerControllerUseCase,
    getQueueListAsFlowUseCase: GetQueueListAsFlowUseCase,
) : ViewModel() {

    private val musicPlayerControllerState = getMusicPlayerControllerUseCase(viewModelScope)

    private val musicPlayerController: MusicPlayerController?
        get() = musicPlayerControllerState.value.data

    private val _queueListState = getQueueListAsFlowUseCase()

    private val _state = MutableStateFlow(MusicQueueListState())

    val state: StateFlow<MusicQueueListState> =
        combine(_state, _queueListState) { state, queueListState ->
            if (queueListState !is Status.Success) {
                return@combine MusicQueueListState()
            }
            val items = queueListState.data
            return@combine state.copy(
                isLoading = false,
                items = items
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MusicQueueListState())


    fun onEvent(event: MusicQueueListEvent) {
        val controller = musicPlayerController ?: return

        when (event) {
            is MusicQueueListEvent.Play -> controller.play(event.index)
            is MusicQueueListEvent.Reorder -> TODO()
            MusicQueueListEvent.ShuffleQueue -> TODO()
            is MusicQueueListEvent.SortByOrder -> TODO()
            is MusicQueueListEvent.SortByType -> TODO()
        }
    }

}