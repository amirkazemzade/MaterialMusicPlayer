package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.media3.session.MediaController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerBottomSheetContent(
    state: SheetState,
    playerState: PlayerState?,
    mediaController: MediaController?,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    AnimatedContent(
        modifier = modifier,
        targetState = state.currentValue,
        transitionSpec = {
            slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(durationMillis = 500),
            ) togetherWith slideOutVertically(animationSpec = tween(1000))
        },
        label = "player_mode_conversion",
    ) { currentValue ->
        if (currentValue == SheetValue.Expanded) {
            FullScreenPlayer(
                modifier = Modifier,
                playerState = playerState,
                mediaController = mediaController,
            )
        } else {
            MiniPlayer(
                modifier = Modifier,
                playerState = playerState,
                mediaController = mediaController,
                onExpand = { scope.launch { state.expand() } },
            )
        }
    }
}
