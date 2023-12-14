package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@Composable
fun TopActionBar(
    onMinimize: () -> Unit,
    onMoreOptions: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMinimize) {
            Icon(
                imageVector = Icons.Default.ExpandMore,
                contentDescription = stringResource(R.string.minimize_player)
            )
        }

        Text("Now Playing", style = MaterialTheme.typography.titleMedium)

        IconButton(onClick = onMoreOptions) {
            Icon(
                imageVector = Icons.Default.MoreVert, contentDescription = "More Options"
            )
        }
    }
}

@Preview
@Composable
fun TopActionBarPreview() {
    MaterialMusicPlayerTheme {
        Surface {
            TopActionBar(
                onMinimize = {},
                onMoreOptions = {}
            )
        }
    }
}