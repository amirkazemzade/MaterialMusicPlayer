package me.amirkazemzade.materialmusicplayer.features.musiclist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class MusicListCoordinator(
    val viewModel: MusicListViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun doStuff() {
        // TODO Handle UI Action
    }
}

@Composable
fun rememberMusicListCoordinator(
    viewModel: MusicListViewModel = hiltViewModel()
): MusicListCoordinator {
    return remember(viewModel) {
        MusicListCoordinator(
            viewModel = viewModel
        )
    }
}