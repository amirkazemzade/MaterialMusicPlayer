package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaMetadata
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.amirkazemzade.materialmusicplayer.domain.model.RepeatMode
import me.amirkazemzade.materialmusicplayer.presentation.common.MusicTimelineGeneratorMock
import me.amirkazemzade.materialmusicplayer.presentation.common.components.CenteredCircularLoading
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.events.PlayerEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.BottomActionBar
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.PlayerControllers
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.Timeline
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.TopActionBar
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.metadata.FullScreenMetadata
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.MusicQueueList
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.TimelineState
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@Composable
fun FullScreenPlayer(
    playerState: PlayerState,
    showQueue: Boolean,
    timelineStateFlow: StateFlow<TimelineState>,
    onMinimize: () -> Unit,
    onToggleQueue: (showQueue: Boolean) -> Unit,
    onEvent: (event: PlayerEvent) -> Unit,
    onToggleFavorite: (value: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler {
        onMinimize()
    }

    if (playerState.mediaMetadata == null) {
        CenteredCircularLoading(modifier = modifier.fillMaxSize())
    } else {

        FullScreenPlayerContent(
            modifier = modifier
                .systemBarsPadding(),
            mediaMetadata = playerState.mediaMetadata,
            isPlaying = playerState.isPlaying,
            canSkipToNext = playerState.canSkipToNext,
            shuffleModeEnabled = playerState.shuffleModeEnabled,
            repeatMode = playerState.repeatMode,
            timelineStateFlow = timelineStateFlow,
            showQueue = showQueue,
            onMinimize = onMinimize,
            onToggleQueue = onToggleQueue,
            onEvent = onEvent,
            onToggleFavorite = onToggleFavorite,
        )
    }
}

@Composable
private fun FullScreenPlayerContent(
    mediaMetadata: MediaMetadata,
    isPlaying: Boolean,
    canSkipToNext: Boolean,
    shuffleModeEnabled: Boolean,
    repeatMode: RepeatMode,
    timelineStateFlow: StateFlow<TimelineState>,
    showQueue: Boolean,
    onMinimize: () -> Unit,
    onToggleQueue: (showQueue: Boolean) -> Unit,
    onEvent: (event: PlayerEvent) -> Unit,
    onToggleFavorite: (value: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 14.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopActionBar(
            onMinimize = onMinimize,
            onMoreOptions = {
                // TODO: implement more options menu
            },
        )
        if (!showQueue) {
            FullScreenMetadata(
                modifier = Modifier.weight(1f),
                mediaMetadata = mediaMetadata,
                isPlaying = isPlaying,
                isFavorite = false,
                onToggleFavorite = onToggleFavorite
            )
        } else {
            MusicQueueList(
                modifier = Modifier.weight(1f),
            )
        }
        Timeline(
            timelineStateFlow = timelineStateFlow,
            onCurrentPositionChange = { positionMs -> onEvent(PlayerEvent.SeekTo(positionMs)) },
        )
        PlayerControllers(
            shuffleActive = shuffleModeEnabled,
            isPlaying = isPlaying,
            canSkipToNext = canSkipToNext,
            repeatMode = repeatMode,
            onPlay = { onEvent(PlayerEvent.Play) },
            onPause = { onEvent(PlayerEvent.Pause) },
            onPrevious = { onEvent(PlayerEvent.Previous) },
            onNext = { onEvent(PlayerEvent.Next) },
            onShuffleChange = { enable -> onEvent(PlayerEvent.ShuffleChange(enable)) },
            onRepeatModeChange = { repeatMode -> onEvent(PlayerEvent.RepeatModeChange(repeatMode)) }
        )
        Spacer(modifier = Modifier.height(32.dp))
        BottomActionBar(
            showQueue = showQueue,
            onShare = {
                // TODO: implement share
            },
            onToggleQueue = onToggleQueue,
        )
    }
}


@Preview
@PreviewLightDark
@Composable
private fun FullScreenPlayerPreview() {
    val timelineMock = MusicTimelineGeneratorMock()

    val position = timelineMock.state.collectAsState()
    val isPlaying = remember { mutableStateOf(true) }

    val timelineStateFlow = MutableStateFlow(TimelineState())

    LaunchedEffect(key1 = position) {
        timelineStateFlow.value = TimelineState(
            duration = timelineMock.duration,
            currentPosition = position.value,
        )
    }

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
                ),
                onMinimize = {},
                onEvent = { event ->
                    when (event) {
                        PlayerEvent.Next -> {
                            timelineMock.setPosition(0)
                        }

                        PlayerEvent.Pause -> {
                            isPlaying.value = false
                        }

                        PlayerEvent.Play -> {
                            isPlaying.value = true
                        }

                        PlayerEvent.Previous -> {
                            timelineMock.setPosition(0)
                        }

                        is PlayerEvent.SeekTo -> {
                            timelineMock.setPosition(event.positionMs)
                        }

                        else -> {}
                    }
                },
                onToggleFavorite = {},
                modifier = Modifier.padding(it),
                timelineStateFlow = timelineStateFlow,
                onToggleQueue = {},
                showQueue = false
            )
        }
    }
}