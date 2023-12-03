package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerBottomSheetContent(
    modifier: Modifier = Modifier,
    state: SheetState
) {
    val scope = rememberCoroutineScope()

    if (state.currentValue == SheetValue.Expanded) {
        FullScreenPlayer(modifier = modifier)
    } else {
        MiniPlayer(
            modifier = modifier,
            onExpand = { scope.launch { state.expand() } }
        )
    }
}
