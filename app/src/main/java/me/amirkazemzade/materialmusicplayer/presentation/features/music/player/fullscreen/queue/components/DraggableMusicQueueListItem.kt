package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.events.MusicQueueListEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.states.DraggableListItemState
import kotlin.math.roundToInt

@Composable
fun DraggableMusicQueueListItem(
    height: Dp,
    index: Int,
    queueItem: QueueItemWithMusic,
    draggingItemIndex: Int?,
    currentPositionIndex: Int?,
    onDragStart: CoroutineScope.(startedPosition: Offset) -> Unit,
    onDragStopped: CoroutineScope.(velocity: Float) -> Unit,
    onUpdateCurrentPosition: (Int) -> Unit,
    onEvent: (event: MusicQueueListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    val density = LocalDensity.current
    val heightPx = with(density) { height.toPx() }

    val translationY = remember {
        Animatable(0f)
    }

    val draggableState = rememberDraggableState { dragAmount ->
        scope.launch { translationY.snapTo(translationY.value + dragAmount) }

        if (draggingItemIndex == null) return@rememberDraggableState

        val positionChangeAmount = (translationY.value / heightPx).roundToInt()
        val newPositionIndex = positionChangeAmount + (draggingItemIndex)
        if (newPositionIndex != currentPositionIndex) {
            onUpdateCurrentPosition(newPositionIndex)
        }
    }

    LaunchedEffect(key1 = currentPositionIndex) {
        val dragState = DraggableListItemState.fromIndices(
            index = index,
            draggingItemIndex = draggingItemIndex,
            currentPositionIndex = currentPositionIndex,
        )

        scope.launch {
            when (dragState) {
                DraggableListItemState.None -> translationY.snapTo(0f)
                DraggableListItemState.DraggedBefore -> translationY.animateTo(heightPx)
                DraggableListItemState.DraggedAfter -> translationY.animateTo(-heightPx)
                DraggableListItemState.Unchanged -> translationY.animateTo(0f)
                DraggableListItemState.Dragging -> {}
            }
        }
    }

    MusicQueueListItem(
        music = queueItem.music,
        draggableState = draggableState,
        isDragging = draggingItemIndex == index,
        onClick = { onEvent(MusicQueueListEvent.Play(index)) },
        onDragStart = onDragStart,
        onDragStopped = { velocity ->
            scope.launch {
                if (draggingItemIndex != null && currentPositionIndex != null) {
                    translationY.animateTo((currentPositionIndex - draggingItemIndex) * heightPx)
                }
            }
            onDragStopped(velocity)
        },
        modifier = modifier
            .height(height)
            .graphicsLayer {
                this.translationY = translationY.value
            }
    )
}