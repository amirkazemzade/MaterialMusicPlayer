package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.amirkazemzade.materialmusicplayer.presentation.common.components.AlbumCover
import me.amirkazemzade.materialmusicplayer.presentation.common.toImageBitmap

@Composable
fun AnimatedAlbumCover(
    artworkData: ByteArray?,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = artworkData,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(600),
            ) togetherWith fadeOut(animationSpec = tween(600))
        },
        label = "title_artist_text_conversion",
    ) { data ->
        val image = data?.toImageBitmap()
        AlbumCover(
            cover = image,
            isLoading = isLoading
        )
    }
}
