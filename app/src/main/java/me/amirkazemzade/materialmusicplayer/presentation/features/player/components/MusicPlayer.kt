package me.amirkazemzade.materialmusicplayer.presentation.features.player.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun MusicPlayer(
//    musicFile: MusicFile,
    musicUri: Uri,
    viewModel: MusicPlayerViewModel = koinViewModel { parametersOf(musicUri) }
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).also {
                it.player = viewModel.player
            }
        }
    )
}
