package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic
import me.amirkazemzade.materialmusicplayer.presentation.common.extensions.withRepositionedElement
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.events.MusicQueueListEvent
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyColumnState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicQueueDraggableList(
    items: ImmutableList<QueueItemWithMusic>,
    onEvent: (event: MusicQueueListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    var queueItems by remember(items) {
        mutableStateOf(items)
    }

    val lazyListState = rememberLazyListState()
    val reorderableLazyColumnState =
        rememberReorderableLazyColumnState(lazyListState = lazyListState) { from, to ->
            queueItems = queueItems.withRepositionedElement(from.index, to.index)
        }

    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        itemsIndexed(
            items = queueItems,
            key = { _, item -> item.id },
        ) { index, queueItem ->

            ReorderableItem(
                reorderableLazyListState = reorderableLazyColumnState,
                key = queueItem.id
            ) { isDragging ->
                MusicQueueListItem(
                    music = queueItem.music,
                    isDragging = isDragging,
                    reorderableItemScope = this,
                    onClick = { onEvent(MusicQueueListEvent.Play(index)) },
                    onDragStopped = {
                        onEvent(MusicQueueListEvent.Reorder(queueItem.id, index))
                    }
                )
            }
        }
    }
}
