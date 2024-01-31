package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.events.MusicQueueListEvent

@ExperimentalFoundationApi
@Composable
fun MusicQueueListContent(
    items: ImmutableList<QueueItemWithMusic>,
    onEvent: (event: MusicQueueListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentModifier = modifier
        .fillMaxSize()

    if (items.isEmpty()) {
        MusicQueueListEmptyContent(
            modifier = contentModifier
        )
    } else {
        MusicQueueListDataReadyContent(
            items = items,
            onEvent = onEvent,
            modifier = contentModifier
        )
    }
}
