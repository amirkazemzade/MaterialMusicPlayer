package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.AnimatedAlbumCover

@Composable
fun FullScreenAlbumCover(
    artworkData: ByteArray?,
    modifier: Modifier = Modifier,
) {
    AnimatedAlbumCover(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp)),
        artworkData = artworkData,
    )
}
