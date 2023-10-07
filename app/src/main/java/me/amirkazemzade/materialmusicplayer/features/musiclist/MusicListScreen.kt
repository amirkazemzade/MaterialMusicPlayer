package me.amirkazemzade.materialmusicplayer.features.musiclist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MusicListScreen(
    state: MusicListState = MusicListState(),
    actions: MusicListActions = MusicListActions()
) {
    // TODO UI Logic
}

@Composable
@Preview(name = "MusicList")
private fun MusicListScreenPreview() {
    MusicListScreen()
}

