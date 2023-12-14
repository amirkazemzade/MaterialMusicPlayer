package me.amirkazemzade.materialmusicplayer.presentation.features.music.player

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.MusicPlayerBottomSheetContent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.PlayerStateProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheetScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    PlayerStateProvider { playerState, onEvent ->
        BottomSheetScaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            sheetContent = {
                MusicPlayerBottomSheetContent(
                    state = scaffoldState.bottomSheetState,
                    playerState = playerState,
                    onEvent = onEvent,
                )
            },
            sheetShape = RectangleShape,
            sheetPeekHeight = if (playerState.isAvailable) 80.dp else 0.dp,
            sheetTonalElevation = 3.dp,
            sheetDragHandle = {},
            content = content,
        )
    }
}