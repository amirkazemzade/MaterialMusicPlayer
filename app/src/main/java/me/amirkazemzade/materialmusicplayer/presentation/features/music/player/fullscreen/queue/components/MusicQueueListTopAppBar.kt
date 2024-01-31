package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MusicQueueListTopAppBar() {
    OrderingTopAppBar(
        modifier = Modifier
            .statusBarsPadding(),
    )
}