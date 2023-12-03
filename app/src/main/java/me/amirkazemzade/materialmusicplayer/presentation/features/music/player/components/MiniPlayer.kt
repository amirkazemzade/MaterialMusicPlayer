package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.common.toImageBitmap
import me.amirkazemzade.materialmusicplayer.presentation.common.AlbumCover
import org.koin.compose.rememberKoinInject

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    onExpand: () -> Unit
) {
    val player = rememberKoinInject<Player>()
    Row(modifier = modifier.fillMaxWidth().clickable { onExpand() }) {
        val image = player.mediaMetadata.artworkData?.toImageBitmap()

        AlbumCover(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(20.dp)),
            cover = image
        )
        Spacer(modifier = modifier.width(16.dp))
        Column(
            modifier = Modifier
                .height(48.dp)
                .weight(1.0f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = player.mediaMetadata.title.toString(),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = player.mediaMetadata.artist.toString(),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                player.seekToPreviousMediaItem()
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_skip_previous_24),
                    contentDescription = stringResource(R.string.play)
                )
            }
            if (player.isPlaying) {
                IconButton(onClick = {
                    player.pause()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_pause_24),
                        contentDescription = stringResource(R.string.play)
                    )
                }
            } else {
                IconButton(onClick = { player.play() }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_play_arrow_24),
                        contentDescription = stringResource(R.string.play)
                    )
                }
            }
            IconButton(onClick = { player.seekToNextMediaItem() }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_skip_next_24),
                    contentDescription = stringResource(R.string.play)
                )
            }
        }
    }
}
