package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.QueueMusic
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@Composable
fun BottomActionBar(
    onShare: () -> Unit,
    onOpenMusicQueue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onShare) {
            Icon(imageVector = Icons.Outlined.Share, contentDescription = "Share")
        }
        IconButton(onClick = onOpenMusicQueue) {
            Icon(imageVector = Icons.AutoMirrored.Outlined.QueueMusic, contentDescription = "Share")
        }
    }
}

@Preview
@Composable
fun BottomActionBarPreview() {
    MaterialMusicPlayerTheme {
        Surface {
            BottomActionBar(
                onShare = {},
                onOpenMusicQueue = {}
            )
        }
    }
}