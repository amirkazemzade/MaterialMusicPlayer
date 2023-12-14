package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MiniTitleAndArtist(
    playerState: PlayerState,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = playerState.mediaMetadata,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -200 },
                animationSpec = tween(durationMillis = 500),
            ) togetherWith slideOutHorizontally(animationSpec = tween(500))
        },
        label = "title_artist_text_conversion",
    ) { mediaMetadata ->
        Column(
            modifier = Modifier.height(50.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                modifier = Modifier.basicMarquee(),
                text = mediaMetadata?.title?.toString() ?: "",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.basicMarquee(),
                text = mediaMetadata?.artist?.toString() ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
