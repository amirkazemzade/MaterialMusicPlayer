package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import me.amirkazemzade.materialmusicplayer.presentation.common.defaults.MaterialMusicPlayerDefaults
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.defaults.PlayerBottomSheetDefaults
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.events.PlayerEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.FullScreenPlayer
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.MiniPlayer
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.TimelineState
import kotlin.math.roundToInt

const val TAG = "BottomSheet"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerBottomSheetContent(
    state: SheetState,
    playerState: PlayerState,
    timelineStateFlow: StateFlow<TimelineState>,
    onEvent: (event: PlayerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics

    val maxHeight = displayMetrics.heightPixels
    val minHeight = PlayerBottomSheetDefaults.SHEET_MIN_PICK_HEIGHT

    var bottomSheetHeight by remember {
        mutableStateOf(minHeight.dp)
    }

    val isExpanded = state.currentValue == SheetValue.Expanded

    LaunchedEffect(key1 = state.targetValue) {
        while (isActive && state.targetValue != state.currentValue) {
            try {
                val offset = state.requireOffset()
                bottomSheetHeight = displayMetrics.heightPixels.dp - offset.roundToInt().dp
            } catch (e: Exception) {
                Log.e(TAG, "Failure in getting offset: ${e.message}", e)
            }
            delay(MaterialMusicPlayerDefaults.SCREEN_UPDATE_INTERVAL_MS)
        }

        bottomSheetHeight =
            if (isExpanded) maxHeight.dp else minHeight.dp
    }

    val fullScreenVisibility = (bottomSheetHeight.value - minHeight) / (maxHeight - minHeight)

    Box(modifier = modifier) {
        if (state.targetValue != state.currentValue) {
            FullScreenPlayer(
                modifier = Modifier
                    .alpha(fullScreenVisibility),
                playerState = playerState,
                timelineStateFlow = timelineStateFlow,
                onMinimize = { scope.launch { state.partialExpand() } },
                onEvent = onEvent,
                onFavoriteChange = { /* TODO: implement favorites */ },
            )
            MiniPlayer(
                modifier = Modifier
                    .alpha(1 - fullScreenVisibility),
                playerState = playerState,
                onExpand = { scope.launch { state.expand() } },
                onEvent = onEvent,
            )
        } else if (isExpanded) {
            FullScreenPlayer(
                playerState = playerState,
                timelineStateFlow = timelineStateFlow,
                onMinimize = { scope.launch { state.partialExpand() } },
                onEvent = onEvent,
                onFavoriteChange = { /* TODO: implement favorites */ },
            )
        } else {
            MiniPlayer(
                playerState = playerState,
                onExpand = { scope.launch { state.expand() } },
                onEvent = onEvent,
            )
        }
    }
}