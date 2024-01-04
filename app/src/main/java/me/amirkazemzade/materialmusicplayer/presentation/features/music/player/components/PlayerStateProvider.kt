package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import me.amirkazemzade.materialmusicplayer.presentation.features.music.MusicControllerViewModel
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.events.PlayerEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.TimelineState
import org.koin.compose.koinInject

@Composable
fun PlayerStateProvider(
    viewModel: MusicControllerViewModel = koinInject(),
    content: @Composable (
        playerState: PlayerState,
        timelineStateFlow: StateFlow<TimelineState>,
        onEvent: (event: PlayerEvent) -> Unit,
    ) -> Unit,
) {
    val playerState = viewModel.playerState.collectAsStateWithLifecycle()
    content(playerState.value, viewModel.timelineState, viewModel::onEvent)
}
