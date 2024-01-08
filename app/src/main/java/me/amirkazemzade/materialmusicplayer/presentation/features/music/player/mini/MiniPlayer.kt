package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.events.PlayerEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.components.MiniAlbumCover
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.components.MiniControllers
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.mini.components.MiniTitleAndArtist
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState

@Composable
fun MiniPlayer(
    playerState: PlayerState,
    onExpand: () -> Unit,
    onEvent: (event: PlayerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
        modifier
            .navigationBarsPadding()
            .fillMaxHeight()
            .clickable { onExpand() }
            .padding(vertical = 16.dp)
            .padding(start = 19.dp, end = 8.dp),
    ) {
        if (playerState.mediaMetadata == null) {
            CircularProgressIndicator()
        } else {
            MiniAlbumCover(
                artworkData = playerState.mediaMetadata.artworkData,
                isLoading = playerState.isLoading,
            )
            Spacer(modifier = Modifier.width(16.dp))
            MiniTitleAndArtist(
                modifier = Modifier.weight(1f),
                metaData = playerState.mediaMetadata,
            )
            Spacer(modifier = Modifier.width(8.dp))
            MiniControllers(
                isPlaying = playerState.isPlaying,
                canSkipToNext = playerState.canSkipToNext,
                onPlay = { onEvent(PlayerEvent.Play) },
                onPause = { onEvent(PlayerEvent.Pause) },
                onPrevious = { onEvent(PlayerEvent.Previous) },
                onNext = { onEvent(PlayerEvent.Next) },
            )
        }
    }
}


