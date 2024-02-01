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

    fun onDragStart(index: Int) {
        updateDraggingItemIndex(index)
    }

    fun onDragStopped() {
        val fromIndex = state.value.draggingItemIndex?.coerceAtLeast(0)
        val toIndex = state.value.currentPositionIndex?.coerceAtMost(state.value.items.lastIndex)

        if (fromIndex == null || toIndex == null) return

        reorderItem(fromIndex, toIndex)
    }

    fun updateCurrentPositionIndex(currentPositionIndex: Int) {
        if (currentPositionIndex == state.value.currentPositionIndex) return
        _state.update {
            it.copy(
                currentPositionIndex = currentPositionIndex,
            )
        }
    }

    private fun updateDraggingItemIndex(draggingItemIndex: Int) {
        if (draggingItemIndex == state.value.draggingItemIndex) return
        _state.update {
            it.copy(
                draggingItemIndex = draggingItemIndex,
            )
        }
    }

    private fun reorderItem(fromIndex: Int, toIndex: Int) {
        val newItems = state.value.items
            .withRepositionedElement(
                fromIndex = fromIndex,
                toIndex = toIndex,
            )
        _state.update {
            it.copy(
                items = newItems,
                draggingItemIndex = null,
                currentPositionIndex = null,
            )
        }
    }
}