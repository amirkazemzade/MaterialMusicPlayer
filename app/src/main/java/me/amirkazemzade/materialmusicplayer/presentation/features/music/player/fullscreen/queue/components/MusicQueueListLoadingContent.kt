package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.amirkazemzade.materialmusicplayer.presentation.common.components.CenteredCircularLoading

@Composable
fun MusicQueueListLoadingContent(
    modifier: Modifier = Modifier,
) {
    CenteredCircularLoading(
        modifier = modifier.fillMaxSize()
    )
}
