package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaMetadata
import me.amirkazemzade.materialmusicplayer.presentation.common.MusicTimelineGeneratorMock
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.MusicEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.BottomActionBar
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.FullScreenAlbumCover
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.PlayerControllers
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.Timeline
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.TitleAndArtist
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.TopActionBar
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@Composable
fun FullScreenPlayer(
    playerState: PlayerState,
    onMinimize: () -> Unit,
    onEvent: (event: MusicEvent) -> Unit,
    onFavoriteChange: (value: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    if (playerState.mediaMetadata == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier
                .padding(horizontal = 14.dp)
                .padding(vertical = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val mediaMetadata = playerState.mediaMetadata

            TopActionBar(
                onMinimize = onMinimize,
                onMoreOptions = {
                    // TODO: implement more options menu
                },
            )
            Spacer(modifier = Modifier.weight(2f))
            FullScreenAlbumCover(
                artworkData = mediaMetadata.artworkData,
            )
            Spacer(modifier = Modifier.weight(2f))
            TitleAndArtist(
                title = mediaMetadata.title?.toString(),
                artist = mediaMetadata.artist?.toString(),
            )
            Spacer(modifier = Modifier.weight(1f))
            Timeline(
                currentPositionMs = playerState.currentPosition,
                durationMs = playerState.duration,
                onCurrentPositionChange = { positionMs -> onEvent(MusicEvent.SeekTo(positionMs)) },
            )
            PlayerControllers(
                shuffleActive = playerState.shuffleModeEnabled,
                isFavorite = false,
                isPlaying = playerState.isPlaying,
                canSkipToNext = playerState.canSkipToNext,
                onShuffleChange = { onEvent(MusicEvent.ShuffleChange(it)) },
                onFavoriteChange = onFavoriteChange,
                onPlay = { onEvent(MusicEvent.Play) },
                onPause = { onEvent(MusicEvent.Pause) },
                onPrevious = { onEvent(MusicEvent.Previous) },
                onNext = { onEvent(MusicEvent.Next) },
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomActionBar(
                onShare = {
                    // TODO: implement share
                },
                onOpenMusicQueue = {
                    // TODO: implement music queue
                },
            )
        }
    }
}

@Preview
@PreviewLightDark
@Composable
fun FullScreenPlayerPreview() {
    val timelineMock = MusicTimelineGeneratorMock()

    val position = timelineMock.state.collectAsState()
    val isPlaying = remember { mutableStateOf(true) }
    MaterialMusicPlayerTheme {
        Scaffold {
            FullScreenPlayer(
                playerState = PlayerState(
                    isAvailable = true,
                    isPlaying = isPlaying.value,
                    mediaMetadata = MediaMetadata
                        .Builder()
                        .setArtist("Taylor Swift")
                        .setTitle("Speak Now (Taylor’s Version)")
                        .build(),
                    currentPosition = position.value,
                    duration = timelineMock.duration,
                ),
                onMinimize = {},
                onFavoriteChange = {},
                onEvent = { event ->
                    when (event) {
                        MusicEvent.Next -> {
                            timelineMock.setPosition(0)
                        }

                        MusicEvent.Pause -> {
                            isPlaying.value = false
                        }

                        MusicEvent.Play -> {
                            isPlaying.value = true
                        }

                        MusicEvent.Previous -> {
                            timelineMock.setPosition(0)
                        }

                        is MusicEvent.SeekTo -> {
                            timelineMock.setPosition(event.positionMs)
                        }

                        else -> {}
                    }
                },
                modifier = Modifier.padding(it),
            )
        }
    }
}