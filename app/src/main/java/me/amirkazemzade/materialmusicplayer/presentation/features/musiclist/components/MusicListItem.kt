package me.amirkazemzade.materialmusicplayer.presentation.features.musiclist.components

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@Composable
fun MusicListItem(
    modifier: Modifier = Modifier,
    music: MusicFile,
    isFavorite: Boolean = false,
    onClick: (music: MusicFile) -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick(music) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (music.albumCover != null) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(20.dp)),
                bitmap = BitmapFactory.decodeByteArray(music.albumCover, 0, music.albumCover.size)
                    .asImageBitmap(),
                contentDescription = "Song's Album Cover Image"
            )
        } else {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.inverseSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(id = R.drawable.baseline_music_note_24),
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    contentDescription = "Music Note Icon"
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .height(48.dp)
                .weight(1.0f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = music.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = music.artist,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(
                    id = if (isFavorite) {
                        R.drawable.baseline_favorite_24
                    } else {
                        R.drawable.baseline_favorite_border_24
                    }
                ),
                contentDescription = "Favorite Icon Button"
            )
        }
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                contentDescription = "Favorite Icon Button"
            )
        }
    }
}

@Preview(name = "MusicListItem")
@Composable
private fun PreviewMusicListItem() {
    MaterialMusicPlayerTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            val music = MusicFile(
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
            )
            MusicListItem(music = music, isFavorite = true, onClick = {})
        }
    }
}

@Preview(name = "LongNameMusicListItem")
@Composable
private fun PreviewLongNameMusicListItem() {
    MaterialMusicPlayerTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            val music = MusicFile(
                id = 1000000033,
                title = "2step (feat. Lil Baby) 2step (feat. Lil Baby) 2step (feat. Lil Baby)" +
                    " 2step (feat. Lil Baby) ",
                artist = "Ed Sheeran Ed Sheeran Ed Sheeran Ed Sheeran Ed Sheeran Ed Sheeran " +
                    "Ed Sheeran Ed Sheeran",
                album = "2step (feat. Lil Baby)",
                filePath = "/storage/emulated/0/Download/01 - 2 step (feat.Lil Baby).mp3",
                dateAdded = "1696781534",
                dateModified = "1696781534",
                duration = "163500",
                genre = "Pop",
                year = "2022",
                uri = Uri.EMPTY
            )
            MusicListItem(music = music, isFavorite = true, onClick = {})
        }
    }
}
