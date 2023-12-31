package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import me.amirkazemzade.materialmusicplayer.R

@Composable
fun AnimatedPlayPauseButton(
    playPauseButtonCornerRadius: Dp,
    isPlaying: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
) {
    FilledTonalIconButton(
        modifier = modifier,
        shape = RoundedCornerShape(playPauseButtonCornerRadius),
        onClick = {
            if (isPlaying) {
                onPause()
            } else {
                onPlay()
            }
        },
    ) {
        Crossfade(
            targetState = isPlaying,
            label = "play_pause_icon_conversion",
            animationSpec = tween(
                durationMillis = 100
            )
        ) { isPlaying ->
            Icon(
                modifier = iconModifier,
                imageVector = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                contentDescription =
                stringResource(
                    if (isPlaying) {
                        R.string.pause
                    } else {
                        R.string.play
                    },
                ),
            )
        }
    }
}
