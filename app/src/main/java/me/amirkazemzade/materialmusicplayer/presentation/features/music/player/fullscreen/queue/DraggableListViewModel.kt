package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic
import me.amirkazemzade.materialmusicplayer.presentation.common.extensions.withRepositionedElement
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.states.DraggableListState

class DraggableListViewModel(val items: ImmutableList<QueueItemWithMusic>) : ViewModel() {
    private var _state = MutableStateFlow(
        DraggableListState(
            items = items,
        )
    )
    val state: StateFlow<DraggableListState>
        get() = _state

    fun reorderItem(fromIndex: Int, toIndex: Int) {
        val newItems = state.value.items
            .withRepositionedElement(
                fromIndex = fromIndex,
                toIndex = toIndex,
            )
        _state.update {
            it.copy(
                items = newItems,
            )
        }
    }
}