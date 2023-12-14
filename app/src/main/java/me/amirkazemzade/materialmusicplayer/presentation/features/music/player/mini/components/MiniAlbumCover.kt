package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.common.components.AlbumCover
import me.amirkazemzade.materialmusicplayer.presentation.common.toImageBitmap
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState

@Composable
fun MiniAlbumCover(
    playerState: PlayerState,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = playerState.mediaMetadata,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(1000),
            ) togetherWith fadeOut(animationSpec = tween(1000))
        },
        label = "title_artist_text_conversion",
    ) { mediaMetadata ->
        val image = mediaMetadata?.artworkData?.toImageBitmap()

        AlbumCover(
            modifier =
            Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp)),
            cover = image,
        )
    }
}
