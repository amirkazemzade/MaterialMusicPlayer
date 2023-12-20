package me.amirkazemzade.materialmusicplayer.presentation.features.music.player

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import me.amirkazemzade.materialmusicplayer.presentation.common.toBitmap
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.MusicPlayerBottomSheetContent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.PlayerStateProvider
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.extractThemeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheetScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (innerPadding: PaddingValues, modifier: Modifier) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    val hazeState = remember { HazeState() }

    PlayerStateProvider { playerState, onEvent ->
        val color = playerState.mediaMetadata?.artworkData?.toBitmap()?.extractThemeColor()
        MaterialMusicPlayerTheme(
            contentColor = color
        ) {
            BottomSheetScaffold(
                modifier = modifier,
                scaffoldState = scaffoldState,
                sheetContainerColor = Color.Transparent,
                sheetContentColor = contentColorFor(MaterialTheme.colorScheme.surface),
                sheetContent = {
                    MusicPlayerBottomSheetContent(
                        modifier = Modifier
                            .hazeChild(state = hazeState),
                        state = scaffoldState.bottomSheetState,
                        playerState = playerState,
                        onEvent = onEvent,
                    )
                },
                sheetShape = RectangleShape,
                sheetPeekHeight = if (playerState.isAvailable) 80.dp else 0.dp,
                sheetTonalElevation = 0.dp,
                sheetShadowElevation = 0.dp,
                sheetDragHandle = {},
            ) { innerPadding ->
                content(
                    innerPadding,
                    Modifier
                        .haze(
                            state = hazeState,
                            backgroundColor = MaterialTheme.colorScheme.surface
                        ),
                )
            }
        }
    }
}