package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.events.MusicQueueListEvent

@Composable
fun MusicQueueListDataReadyContent(
    items: ImmutableList<QueueItemWithMusic>,
    onEvent: (event: MusicQueueListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { MusicQueueListTopAppBar() },
        containerColor = Color.Transparent,
        contentColor = contentColorFor(MaterialTheme.colorScheme.surface),
    ) { innerPadding ->
        MusicQueueDraggableList(
            items = items,
            onEvent = onEvent,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

