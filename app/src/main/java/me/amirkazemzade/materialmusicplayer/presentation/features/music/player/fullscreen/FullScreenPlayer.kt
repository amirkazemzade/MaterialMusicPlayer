package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaMetadata
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.amirkazemzade.materialmusicplayer.presentation.common.MusicTimelineGeneratorMock
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.events.PlayerEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.BottomActionBar
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.FullScreenAlbumCover
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.PlayerControllers
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.Timeline
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.TitleAndArtist
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.TopActionBar
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.MusicQueueList
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.TimelineState
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenPlayer(
    playerState: PlayerState,
    onMinimize: () -> Unit,
    timelineStateFlow: StateFlow<TimelineState>,
    onEvent: (event: PlayerEvent) -> Unit,
    onFavoriteChange: (value: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (playerState.mediaMetadata == null) {
        Loading(modifier = modifier.fillMaxSize())
    } else {
        val scope = rememberCoroutineScope()

        val sheetScaffoldState = rememberBottomSheetScaffoldState()
        val hazeState = remember { HazeState() }

        BottomSheetScaffold(
            modifier = modifier
                .fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = contentColorFor(MaterialTheme.colorScheme.surface),
            sheetContainerColor = Color.Transparent,
            sheetContentColor = contentColorFor(MaterialTheme.colorScheme.surface),
            sheetShadowElevation = 0.dp,
            sheetTonalElevation = 0.dp,
            sheetPeekHeight = 0.dp,
            sheetShape = RectangleShape,
            sheetDragHandle = {},
            scaffoldState = sheetScaffoldState,
            sheetContent = {
                MusicQueueList(
                    modifier = Modifier
                        .hazeChild(hazeState)
                        .systemBarsPadding()
                        .fillMaxSize(),
                )
            }
        ) {
            FullScreenPlayerContent(
                modifier = Modifier
                    .haze(
                        state = hazeState,
                        backgroundColor = MaterialTheme.colorScheme.surface,
                    )
                    .systemBarsPadding(),
                mediaMetadata = playerState.mediaMetadata,
                isPlaying = playerState.isPlaying,
                canSkipToNext = playerState.canSkipToNext,
                shuffleModeEnabled = playerState.shuffleModeEnabled,
                timelineStateFlow = timelineStateFlow,
                onMinimize = onMinimize,
                onOpenMusicQueue = {
                    scope.launch {
                        sheetScaffoldState.bottomSheetState.expand()
                    }
                },
                onEvent = onEvent,
                onFavoriteChange = onFavoriteChange,
            )

        }
    }
}

@Composable
private fun FullScreenPlayerContent(
    mediaMetadata: MediaMetadata,
    isPlaying: Boolean,
    canSkipToNext: Boolean,
    shuffleModeEnabled: Boolean,
    timelineStateFlow: StateFlow<TimelineState>,
    onMinimize: () -> Unit,
    onOpenMusicQueue: () -> Unit,
    onEvent: (event: PlayerEvent) -> Unit,
    onFavoriteChange: (value: Boolean) -> Unit,
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
            timelineStateFlow = timelineStateFlow,
            onCurrentPositionChange = { positionMs -> onEvent(PlayerEvent.SeekTo(positionMs)) },
        )
        PlayerControllers(
            shuffleActive = shuffleModeEnabled,
            isFavorite = false,
            isPlaying = isPlaying,
            canSkipToNext = canSkipToNext,
            onShuffleChange = { onEvent(PlayerEvent.ShuffleChange(it)) },
            onFavoriteChange = onFavoriteChange,
            onPlay = { onEvent(PlayerEvent.Play) },
            onPause = { onEvent(PlayerEvent.Pause) },
            onPrevious = { onEvent(PlayerEvent.Previous) },
            onNext = { onEvent(PlayerEvent.Next) },
        )
        Spacer(modifier = Modifier.weight(1f))
        BottomActionBar(
            onShare = {
                // TODO: implement share
            },
            onOpenMusicQueue = onOpenMusicQueue,
        )
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@PreviewLightDark
@Composable
fun FullScreenPlayerPreview() {
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
                onFavoriteChange = {},
                modifier = Modifier.padding(it),
                timelineStateFlow = timelineStateFlow,
            )
        }
    }
}