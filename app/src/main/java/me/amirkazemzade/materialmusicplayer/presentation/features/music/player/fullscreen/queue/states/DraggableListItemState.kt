package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.states

enum class DraggableListItemState {
    None,
    Dragging,
    DraggedBefore,
    DraggedAfter,
    Unchanged;

    companion object {
        fun fromIndices(
            index: Int,
            draggingItemIndex: Int?,
            currentPositionIndex: Int?,
        ): DraggableListItemState {
            if (draggingItemIndex == null || currentPositionIndex == null) return None
            if (index == draggingItemIndex) return Dragging
            if (index <= currentPositionIndex && index > draggingItemIndex) return DraggedAfter
            if (index >= currentPositionIndex && index < draggingItemIndex) return DraggedBefore
            return Unchanged
        }
    }
}
