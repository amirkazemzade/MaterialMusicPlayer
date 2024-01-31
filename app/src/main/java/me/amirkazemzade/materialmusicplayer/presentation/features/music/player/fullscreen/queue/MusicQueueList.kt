package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components.MusicQueueListContent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components.MusicQueueListLoadingContent
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicQueueList(
    modifier: Modifier = Modifier,
    viewModel: MusicQueueListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isLoading) {
        MusicQueueListLoadingContent(modifier)
    } else {
        MusicQueueListContent(modifier = modifier.fillMaxSize(),
            items = state.items,
            onEvent = { event -> viewModel.onEvent(event) })
    }
}
