package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.presentation.common.components.CenteredNote

@Composable
fun MusicQueueListEmptyContent(
    modifier: Modifier = Modifier,
) {
    CenteredNote(
        modifier = modifier,
        note = stringResource(R.string.queue_is_empty),
    )
}