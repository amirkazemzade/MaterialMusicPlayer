package me.amirkazemzade.materialmusicplayer.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Album
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.R

@Composable
fun AlbumCover(
    cover: ImageBitmap?,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = modifier.padding(12.dp)
        )
    } else if (cover != null) {
        Image(
            modifier = modifier,
            bitmap = cover,
            contentScale = ContentScale.FillBounds,
            contentDescription = stringResource(R.string.song_s_album_cover_image),
        )
    } else {
        Box(
            modifier = modifier.background(MaterialTheme.colorScheme.inverseSurface),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Rounded.Album,
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                contentDescription = stringResource(R.string.music_note_icon),
            )
        }
    }
}
