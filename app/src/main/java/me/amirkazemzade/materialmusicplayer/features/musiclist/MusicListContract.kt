package me.amirkazemzade.materialmusicplayer.features.musiclist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents MusicListScreen
 **/
class MusicListState

/**
 * MusicList Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class MusicListActions(
    val onClick: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalMusicListActions = staticCompositionLocalOf<MusicListActions> {
    error("{NAME} Actions Were not provided, make sure ProvideMusicListActions is called")
}

@Composable
fun ProvideMusicListActions(actions: MusicListActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalMusicListActions provides actions) {
        content.invoke()
    }
}

