package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import me.amirkazemzade.materialmusicplayer.presentation.features.music.MusicEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.FullScreenPlayer
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.MiniPlayer
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerBottomSheetContent(
    state: SheetState,
    playerState: PlayerState,
    onEvent: (event: MusicEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    if (state.targetValue == SheetValue.Expanded) {
        FullScreenPlayer(
            modifier = modifier,
            playerState = playerState,
            onMinimize = { scope.launch { state.partialExpand() } },
            onEvent = onEvent,
            onFavoriteChange = { /* TODO: implement favorites */ },
        )
    } else {
        MiniPlayer(
            modifier = modifier,
            playerState = playerState,
            onExpand = { scope.launch { state.expand() } },
            onEvent = onEvent,
        )
    }
}