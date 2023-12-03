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
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.MusicPlayerBottomSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheetScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        modifier = modifier,
        sheetContent = {
            MusicPlayerBottomSheetContent(
                state = scaffoldState.bottomSheetState
            )
        },
        sheetShape = RectangleShape,
        sheetPeekHeight = 60.dp,
        sheetDragHandle = {
            if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                BottomSheetDefaults.DragHandle()
            }
        },
        content = content
    )
}
