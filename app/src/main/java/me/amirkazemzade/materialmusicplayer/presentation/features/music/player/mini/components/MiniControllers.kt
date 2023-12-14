package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState

@Composable
fun MiniControllers(
    playerState: PlayerState,
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
                painter = painterResource(R.drawable.baseline_skip_previous_24),
                contentDescription = stringResource(R.string.previous),
            )
        }

        val playPauseButtonRadius = if (playerState.isPlaying) 12.dp else 32.dp
        val playPauseButtonCornerRadius =
            animateDpAsState(
                targetValue = playPauseButtonRadius,
                animationSpec = tween(durationMillis = 500),
                label = "play_pause_shape_conversion",
            )

        PlayPauseButton(
            playPauseButtonCornerRadius = playPauseButtonCornerRadius,
            playerState = playerState,
            onPlay = onPlay,
            onPause = onPause,
        )

        IconButton(onClick = onNext) {
            Icon(
                painter = painterResource(R.drawable.baseline_skip_next_24),
                contentDescription = stringResource(R.string.next),
            )
        }
    }
}

@Composable
private fun PlayPauseButton(
    playPauseButtonCornerRadius: State<Dp>,
    playerState: PlayerState,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledIconButton(
        modifier = modifier,
        shape = RoundedCornerShape(playPauseButtonCornerRadius.value),
        onClick = {
            if (playerState.isPlaying) {
                onPause()
            } else {
                onPlay()
            }
        },
    ) {
        Crossfade(
            targetState = playerState.isPlaying,
            label = "play_pause_icon_conversion",
        ) { isPlaying ->
            Icon(
                painter =
                painterResource(
                    if (isPlaying) {
                        R.drawable.baseline_pause_24
                    } else {
                        R.drawable.baseline_play_arrow_24
                    },
                ),
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

