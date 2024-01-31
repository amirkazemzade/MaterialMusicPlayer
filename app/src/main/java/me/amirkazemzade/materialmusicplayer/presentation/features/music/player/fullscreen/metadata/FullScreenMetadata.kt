package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.metadata

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaMetadata
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.FullScreenAlbumCover
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components.MetaDataRow

@Composable
fun FullScreenMetadata(
    mediaMetadata: MediaMetadata,
    isPlaying: Boolean,
    isFavorite: Boolean,
    onToggleFavorite: (isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        FullScreenAlbumCover(
            modifier = Modifier,
            artworkData = mediaMetadata.artworkData,
            isPlaying = isPlaying,
        )
        Spacer(modifier = Modifier.weight(1f))
        MetaDataRow(
            title = mediaMetadata.title?.toString(),
            artist = mediaMetadata.artist?.toString(),
            isFavorite = isFavorite,
            onToggleFavorite = onToggleFavorite,
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
