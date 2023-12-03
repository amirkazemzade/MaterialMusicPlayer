package me.amirkazemzade.materialmusicplayer.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import me.amirkazemzade.materialmusicplayer.R

@Composable
fun AlbumCover(
    modifier: Modifier = Modifier,
    cover: ImageBitmap?
) {
    if (cover != null) {
        Image(
            modifier = modifier,
            bitmap = cover,
            contentDescription = stringResource(R.string.song_s_album_cover_image)
        )
    } else {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.inverseSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(id = R.drawable.baseline_music_note_24),
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                contentDescription = stringResource(R.string.music_note_icon)
            )
        }
    }
}
