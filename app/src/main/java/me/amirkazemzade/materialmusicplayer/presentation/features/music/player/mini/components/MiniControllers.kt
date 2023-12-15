package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.AnimatedPlayPauseButton

@Composable
fun MiniControllers(
    isPlaying: Boolean,
    canSkipToNext: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onPrevious
        ) {
            Icon(
                imageVector = Icons.Rounded.SkipPrevious,
                contentDescription = stringResource(R.string.previous),
            )
        }

        val playPauseButtonRadius = if (isPlaying) 12.dp else 32.dp
        val playPauseButtonCornerRadius =
            animateDpAsState(
                targetValue = playPauseButtonRadius,
                animationSpec = tween(durationMillis = 250),
                label = "play_pause_shape_conversion",
            )

        AnimatedPlayPauseButton(
            playPauseButtonCornerRadius = playPauseButtonCornerRadius.value,
            isPlaying = isPlaying,
            onPlay = onPlay,
            onPause = onPause,
        )

        IconButton(
            onClick = onNext,
            enabled = canSkipToNext,
        ) {
            Icon(
                imageVector = Icons.Rounded.SkipNext,
                contentDescription = stringResource(R.string.next),
            )
        }
    }
}
