package me.amirkazemzade.materialmusicplayer.features.musiclist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MusicListRoute(
    coordinator: MusicListCoordinator = rememberMusicListCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(MusicListState())

    // UI Actions
    val actions = rememberMusicListActions(coordinator)

    // UI Rendering
    MusicListScreen(uiState, actions)
}


@Composable
fun rememberMusicListActions(coordinator: MusicListCoordinator): MusicListActions {
    return remember(coordinator) {
        MusicListActions(
            onClick = coordinator::doStuff
        )
    }
}