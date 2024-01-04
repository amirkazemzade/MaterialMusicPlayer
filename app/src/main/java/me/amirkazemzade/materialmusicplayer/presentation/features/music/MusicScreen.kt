package me.amirkazemzade.materialmusicplayer.presentation.features.music

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.MusicList
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.PlayerBottomSheetScaffold
import org.koin.compose.koinInject

@Composable
fun MusicScreen(
    modifier: Modifier = Modifier,
    musicControllerViewModel: MusicControllerViewModel = koinInject(),
) {
    PlayerBottomSheetScaffold(
        modifier = modifier,
    ) { innerPadding, contentModifier ->
        MusicList(
            contentPadding = innerPadding,
            modifier = contentModifier,
        )
    }
}
