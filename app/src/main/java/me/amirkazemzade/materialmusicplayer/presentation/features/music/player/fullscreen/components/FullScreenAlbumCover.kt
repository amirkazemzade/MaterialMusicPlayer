package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components.AnimatedAlbumCover

@Composable
fun FullScreenAlbumCover(
    artworkData: ByteArray?,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
) {
    val widthFraction = if (isPlaying) 1f else 0.5f
    val widthFractionAnimated by animateFloatAsState(
        targetValue = widthFraction,
        label = "artwork_size_transition",
    )

    AnimatedAlbumCover(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(fraction = widthFractionAnimated)
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.small),
        artworkData = artworkData,
    )
}
