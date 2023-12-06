package me.amirkazemzade.materialmusicplayer.presentation.features.music.player

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.media3.session.MediaController
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.MusicPlayerBottomSheetContent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.PlayerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheetScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    PlayerStateProvider { playerState, mediaController ->
        BottomSheetScaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            sheetContent = {
                MusicPlayerBottomSheetContent(
                    state = scaffoldState.bottomSheetState,
                    playerState = playerState,
                    mediaController = mediaController,
                )
            },
            sheetShape = RectangleShape,
            sheetPeekHeight = getSheetPeekHeight(playerState, mediaController),
            sheetTonalElevation = 3.dp,
            sheetDragHandle = {
                if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                    BottomSheetDefaults.DragHandle()
                }
            },
            content = content,
        )
    }
}

@Composable
private fun getSheetPeekHeight(
    playerState: PlayerState?,
    mediaController: MediaController?,
) = if (
    playerState == null ||
    (playerState.mediaMetadata == null && !playerState.isPlaying) ||
    mediaController?.currentTimeline?.isEmpty == true
) {
    0.dp
} else {
    80.dp
}
