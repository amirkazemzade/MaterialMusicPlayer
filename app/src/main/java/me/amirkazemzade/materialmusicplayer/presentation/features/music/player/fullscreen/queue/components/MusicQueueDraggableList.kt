package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic
import me.amirkazemzade.materialmusicplayer.presentation.common.extensions.withRepositionedElement
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.events.MusicQueueListEvent

@Composable
fun MusicQueueDraggableList(
    items: ImmutableList<QueueItemWithMusic>,
    onEvent: (event: MusicQueueListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val itemHeight = 64.dp

    var lazyListItems by remember {
        mutableStateOf(items)
    }

    var draggingItemIndex by remember {
        mutableStateOf<Int?>(null)
    }
    var currentPositionIndex by remember {
        mutableStateOf<Int?>(null)
    }

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(
            items = lazyListItems,
            key = { index, item -> if (index == 0) 0 else item.id },
        ) { index, queueItem ->

            DraggableMusicQueueListItem(
                index = index,
                height = itemHeight,
                draggingItemIndex = draggingItemIndex,
                currentPositionIndex = currentPositionIndex,
                queueItem = queueItem,
                onEvent = onEvent,
                onDragStart = {
                    draggingItemIndex = index
                },
                onDragStopped = {
                    val fromIndex = draggingItemIndex?.coerceAtLeast(0)
                    val toIndex = currentPositionIndex?.coerceAtMost(lazyListItems.lastIndex)

                    if (fromIndex == null || toIndex == null) return@DraggableMusicQueueListItem

                    val newListItems = lazyListItems
                        .withRepositionedElement(
                            fromIndex = fromIndex,
                            toIndex = toIndex,
                        )
                    lazyListItems = newListItems
                    draggingItemIndex = null
                    currentPositionIndex = null
                },
                onUpdateCurrentPosition = { newPosition ->
                    currentPositionIndex = newPosition
                },
            )
        }
    }
}
