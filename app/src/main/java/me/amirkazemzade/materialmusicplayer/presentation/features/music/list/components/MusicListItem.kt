package me.amirkazemzade.materialmusicplayer.presentation.features.music.list.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.presentation.common.components.AlbumCover
import me.amirkazemzade.materialmusicplayer.presentation.common.toImageBitmap
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@Composable
fun MusicListItem(
    music: MusicFile,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
) {
    Row(
        modifier =
        modifier
            .clickable { onClick() }
            .padding(start = 20.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AlbumCover(
            modifier =
            Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp)),
            cover = music.artwork?.toImageBitmap(),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier =
            Modifier
                .height(48.dp)
                .weight(1.0f),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = music.title ?: stringResource(R.string.unknown),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = music.artist ?: stringResource(R.string.unknown),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        IconButton(onClick = {
            // TODO: implement favorite
        }) {
            Icon(
                imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = "Favorite Icon Button",
            )
        }
        IconButton(onClick = {
            // TODO: implement more options
        }) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = "Favorite Icon Button",
            )
        }
    }
}

@Preview(name = "MusicListItem")
@Composable
private fun PreviewMusicListItem() {
    MaterialMusicPlayerTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            val music =
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
                    uri = Uri.EMPTY,
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
            val music =
                MusicFile(
                    id = 1000000033,
                    title =
                    "2step (feat. Lil Baby) 2step (feat. Lil Baby) 2step (feat. Lil Baby)" +
                            " 2step (feat. Lil Baby) ",
                    artist =
                    "Ed Sheeran Ed Sheeran Ed Sheeran Ed Sheeran Ed Sheeran Ed Sheeran " +
                            "Ed Sheeran Ed Sheeran",
                    album = "2step (feat. Lil Baby)",
                    filePath = "/storage/emulated/0/Download/01 - 2 step (feat.Lil Baby).mp3",
                    dateAdded = "1696781534",
                    dateModified = "1696781534",
                    duration = "163500",
                    genre = "Pop",
                    year = "2022",
                    uri = Uri.EMPTY,
                )
            MusicListItem(music = music, isFavorite = true, onClick = {})
        }
    }
}
