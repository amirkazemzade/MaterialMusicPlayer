package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.DraggableListViewModel
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.events.MusicQueueListEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicQueueDraggableList(
    items: ImmutableList<QueueItemWithMusic>,
    onEvent: (event: MusicQueueListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val itemHeight = 64.dp
    val viewModel = DraggableListViewModel(items)
    val state by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        itemsIndexed(
            items = state.items,
            key = { index, item -> if (index == 0) 0 else item.id },
        ) { index, queueItem ->

            DraggableMusicQueueListItem(
                index = index,
                height = itemHeight,
                totalItemsCount = state.items.size,
                draggingItemIndex = state.draggingItemIndex,
                currentPositionIndex = state.currentPositionIndex,
                queueItem = queueItem,
                onEvent = onEvent,
                onDragStart = {
                    viewModel.onDragStart(index)
                },
                onDragStopped = {
                    viewModel.onDragStopped()
                },
                updateCurrentPosition = { newPosition ->
                    viewModel.updateCurrentPositionIndex(newPosition)
                },
                modifier = Modifier
                    // removing default item placement animation due to conflict with custom item placement animation
                    .animateItemPlacement(animationSpec = tween(durationMillis = 0))
            )
        }
    }
}
