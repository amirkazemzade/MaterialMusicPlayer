package me.amirkazemzade.materialmusicplayer.presentation.features.music.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import me.amirkazemzade.materialmusicplayer.presentation.features.music.MusicViewModel
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.PlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.toPlayerState
import org.koin.compose.koinInject

@Composable
fun PlayerStateProvider(
    musicViewModel: MusicViewModel = koinInject(),
    content: @Composable (
        playerState: PlayerState?,
        mediaController: MediaController?,
    ) -> Unit,
) {
    val mediaControllerState = musicViewModel.mediaControllerState.collectAsStateWithLifecycle()
    val mediaController = mediaControllerState.value.data

    val playerState =
        remember {
            mutableStateOf(mediaController?.toPlayerState())
        }

    DisposableEffect(key1 = mediaControllerState.value) {
        playerState.value = mediaController?.toPlayerState()

        val listener =
            object : Player.Listener {
                override fun onEvents(
                    player: Player,
                    events: Player.Events,
                ) {
                    super.onEvents(player, events)
                    playerState.value = player.toPlayerState()
                }
            }

        mediaController?.addListener(listener)
        onDispose {
            mediaController?.removeListener(listener)
            mediaController?.release()
        }
    }

    content(playerState.value, mediaController)
}
