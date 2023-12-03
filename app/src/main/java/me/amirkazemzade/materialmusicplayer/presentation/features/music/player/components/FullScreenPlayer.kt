package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import org.koin.compose.koinInject

@Composable
fun FullScreenPlayer(
    modifier: Modifier = Modifier
) {
    val player = koinInject<Player>()
    AndroidView(
        modifier = modifier,
        factory = { context ->
            PlayerView(context).also {
                it.player = player
            }
        }
    )
}
