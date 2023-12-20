package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.AnimatedAlbumCover

@Composable
fun MiniAlbumCover(
    artworkData: ByteArray?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedAlbumCover(
        modifier = modifier
            .size(50.dp)
            .clip(RoundedCornerShape(8.dp)),
        artworkData = artworkData,
        isLoading = isLoading,
    )

}