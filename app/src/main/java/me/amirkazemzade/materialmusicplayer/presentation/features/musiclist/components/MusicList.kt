package me.amirkazemzade.materialmusicplayer.presentation.features.musiclist.components

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@Composable
fun MusicList(
    modifier: Modifier = Modifier,
    musics: List<MusicFile>,
    onItemClick: (music: MusicFile) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(musics) { music ->
            MusicListItem(
                modifier = Modifier.fillMaxWidth(),
                music = music,
                onClick = onItemClick
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMusicList() {
    val musicList = buildList {
        repeat(10) {
            addAll(
                listOf(
                    MusicFile(
                        id = 1000000033,
                        title = "2step (feat. Lil Baby)",
                        artist = "Ed Sheeran",
                        album = "2step (feat. Lil Baby)",
                        filePath = "/storage/emulated/0/Download/01 - 2 step (feat.Lil Baby).mp3",
                        dateAdded = "1696781534",
                        dateModified = "1696781534",
                        duration = "163500",
                        genre = "Pop",
                        year = "2022",
                        uri = Uri.EMPTY
                    ),
                    MusicFile(
                        id = 1000000034,
                        title = "Bad For Me(feat.Teddy Swims)",
                        artist = "Meghan Trainor",
                        album = "Bad For Me(feat.Teddy Swims)",
                        filePath = "/storage/emulated/0/Download/01 - " +
                            "Bad For Me(feat.Teddy Swims).mp3",
                        dateAdded = "1696781534",
                        dateModified = "1696781535",
                        duration = "213342",
                        genre = null,
                        year = "2022",
                        uri = Uri.EMPTY
                    ),
                    MusicFile(
                        id = 1000000035,
                        title = "Left and Right(Feat.Jung Kook of BTS)",
                        artist = "Charlie Puth",
                        album = "Left and Right(Feat.Jung Kook of BTS)",
                        filePath = "/storage/emulated/0/Download/01 - " +
                            "Left and Right(Feat.Jung Kook of BTS).mp3",
                        dateAdded = "1696781535",
                        dateModified = "1696781536",
                        duration = "154514",
                        genre = "Pop",
                        year = "2022",
                        uri = Uri.EMPTY
                    )
                )
            )
        }
    }

    MaterialMusicPlayerTheme {
        Scaffold {
            MusicList(
                modifier = Modifier.padding(it),
                musics = musicList,
                onItemClick = {}
            )
        }
    }
}
