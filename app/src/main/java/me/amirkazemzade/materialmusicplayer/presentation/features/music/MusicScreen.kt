package me.amirkazemzade.materialmusicplayer.presentation.features.music

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.MusicList
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.PlayerBottomSheetScaffold

@Composable
fun MusicScreen(
    modifier: Modifier = Modifier
) {
    PlayerBottomSheetScaffold(modifier = modifier) { innerPadding ->
        MusicList(modifier = Modifier.padding(innerPadding))
    }
}
