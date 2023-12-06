package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.media3.session.MediaController
import me.amirkazemzade.materialmusicplayer.common.toImageBitmap
import me.amirkazemzade.materialmusicplayer.presentation.common.AlbumCover

@Composable
fun FullScreenPlayer(
    playerState: PlayerState?,
    mediaController: MediaController?,
    modifier: Modifier = Modifier,
) {
    if (playerState == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier =
            modifier
                .padding(horizontal = 30.dp)
                .padding(bottom = 3.dp)
                .fillMaxSize(),
        ) {
            val image = playerState.mediaMetadata?.artworkData?.toImageBitmap()

            AlbumCover(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                cover = image,
            )
        }
    }
}
