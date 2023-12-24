package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import me.amirkazemzade.materialmusicplayer.presentation.features.music.MusicControllerViewModel
import me.amirkazemzade.materialmusicplayer.presentation.features.music.MusicEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState
import org.koin.compose.koinInject

@Composable
fun PlayerStateProvider(
    musicControllerViewModel: MusicControllerViewModel = koinInject(),
    content: @Composable (
        playerState: PlayerState,
        onEvent: (event: MusicEvent) -> Unit,
    ) -> Unit,
) {

    val mediaControllerState =
        musicControllerViewModel.mediaControllerState.collectAsStateWithLifecycle()
    val mediaController = mediaControllerState.value.data

    val playerState = remember { mutableStateOf(PlayerState(isAvailable = false)) }

    DisposableEffect(key1 = mediaControllerState.value) {
        onDispose {
            mediaController?.release()
        }
    }

    LaunchedEffect(mediaController?.playbackState) {
        while (isActive) {
            delay(500)
            playerState.value = PlayerState.fromPlayer(mediaController)
        }
    }

    content(playerState.value, musicControllerViewModel::onEvent)
}
