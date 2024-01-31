package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.features.music.components.AnimatedFavoriteIconButton

@Composable
fun MetaDataRow(
    title: String?,
    artist: String?,
    isFavorite: Boolean,
    onToggleFavorite: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = listOf(title, artist),
        transitionSpec = {
            fadeIn(
                animationSpec = tween(500),
            ) togetherWith fadeOut(animationSpec = tween(500))
        },
        label = "title_artist_text_conversion",
    ) { (title, artist) ->
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TitleAndArtist(
                modifier = Modifier.weight(1f),
                title = title,
                artist = artist,
            )
            AnimatedFavoriteIconButton(
                isFavorite = isFavorite,
                onToggleFavorite = onToggleFavorite,
            )
        }
    }
}
