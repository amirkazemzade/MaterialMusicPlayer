package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.media3.session.MediaController
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.common.toImageBitmap
import me.amirkazemzade.materialmusicplayer.presentation.common.AlbumCover

@Composable
fun MiniPlayer(
    playerState: PlayerState?,
    mediaController: MediaController?,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
        modifier
            .fillMaxHeight()
            .clickable { onExpand() }
            .padding(vertical = 16.dp)
            .padding(start = 19.dp, end = 8.dp),
    ) {
        if (playerState == null) {
            CircularProgressIndicator()
        } else {
            MiniAlbumCover(playerState = playerState)
            Spacer(modifier = Modifier.width(16.dp))
            TitleAndArtist(
                modifier = Modifier.weight(1f),
                playerState = playerState,
            )
            Spacer(modifier = Modifier.width(8.dp))
            MiniControllers(
                mediaController = mediaController,
                playerState = playerState,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TitleAndArtist(
    playerState: PlayerState,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = playerState.mediaMetadata,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -200 },
                animationSpec = tween(durationMillis = 500),
            ) togetherWith slideOutHorizontally(animationSpec = tween(500))
        },
        label = "title_artist_text_conversion",
    ) { mediaMetadata ->
        Column(
            modifier = Modifier.height(50.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                modifier = Modifier.basicMarquee(),
                text = mediaMetadata?.title?.toString() ?: "",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.basicMarquee(),
                text = mediaMetadata?.artist?.toString() ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun MiniControllers(
    mediaController: MediaController?,
    playerState: PlayerState,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {
            mediaController?.seekToPreviousMediaItem()
        }) {
            Icon(
                painter = painterResource(R.drawable.baseline_skip_previous_24),
                contentDescription = stringResource(R.string.play),
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
            mediaController = mediaController,
        )

        IconButton(onClick = { mediaController?.seekToNextMediaItem() }) {
            Icon(
                painter = painterResource(R.drawable.baseline_skip_next_24),
                contentDescription = stringResource(R.string.play),
            )
        }
    }
}

@Composable
private fun PlayPauseButton(
    playPauseButtonCornerRadius: State<Dp>,
    playerState: PlayerState,
    mediaController: MediaController?,
) {
    FilledIconButton(shape = RoundedCornerShape(playPauseButtonCornerRadius.value), onClick = {
        if (playerState.isPlaying) {
            mediaController?.pause()
        } else {
            mediaController?.play()
        }
    }) {
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

@Composable
private fun MiniAlbumCover(playerState: PlayerState) {
    AnimatedContent(
        targetState = playerState.mediaMetadata,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(1000),
            ) togetherWith fadeOut(animationSpec = tween(1000))
        },
        label = "title_artist_text_conversion",
    ) { mediaMetadata ->
        val image = mediaMetadata?.artworkData?.toImageBitmap()

        AlbumCover(
            modifier =
            Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp)),
            cover = image,
        )
    }
}
