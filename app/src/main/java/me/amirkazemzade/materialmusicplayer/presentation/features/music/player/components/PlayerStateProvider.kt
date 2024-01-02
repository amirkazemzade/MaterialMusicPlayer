package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import me.amirkazemzade.materialmusicplayer.presentation.features.music.MusicControllerViewModel
import me.amirkazemzade.materialmusicplayer.presentation.features.music.MusicEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.TimelineState
import org.koin.compose.koinInject

@Composable
fun PlayerStateProvider(
    viewModel: MusicControllerViewModel = koinInject(),
    content: @Composable (
        playerState: PlayerState,
        timelineStateFlow: StateFlow<TimelineState>,
        onEvent: (event: MusicEvent) -> Unit,
    ) -> Unit,
) {

    val mediaControllerState =
        viewModel.mediaControllerState.collectAsStateWithLifecycle()
    val mediaController = mediaControllerState.value.data

    val playerState = viewModel.playerState.collectAsStateWithLifecycle()

    DisposableEffect(key1 = mediaControllerState.value) {
        onDispose {
            mediaController?.release()
        }
    }

//    LaunchedEffect(mediaController?.playbackState) {
//        while (isActive) {
//            delay(500)
//            playerState.value = PlayerState.fromPlayer(mediaController)
//        }
//    }

    content(playerState.value, viewModel.timelineState, viewModel::onEvent)
}
