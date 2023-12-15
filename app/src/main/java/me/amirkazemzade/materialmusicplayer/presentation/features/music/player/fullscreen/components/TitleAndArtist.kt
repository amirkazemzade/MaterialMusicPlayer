package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun TitleAndArtist(
    title: String?,
    artist: String?,
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
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.basicMarquee(),
                text = title ?: "",
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.basicMarquee(),
                text = artist ?: "",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
