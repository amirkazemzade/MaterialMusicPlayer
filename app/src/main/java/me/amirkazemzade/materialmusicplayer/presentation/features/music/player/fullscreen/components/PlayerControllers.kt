package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShuffleOn
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.R

@Composable
fun PlayerControllers(
    shuffleActive: Boolean,
    isFavorite: Boolean,
    isPlaying: Boolean,
    onShuffleChange: (value: Boolean) -> Unit,
    onFavoriteChange: (value: Boolean) -> Unit,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        IconToggleButton(
            checked = shuffleActive,
            onCheckedChange = onShuffleChange,
        ) {
            if (shuffleActive) {
                Icon(
                    imageVector = Icons.Filled.ShuffleOn,
                    contentDescription = stringResource(R.string.shuffle_is_on)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Shuffle,
                    contentDescription = stringResource(R.string.shuffle_is_off)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = onPrevious,
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = stringResource(R.string.previous)
            )
        }
        IconButton(
            modifier = Modifier.size(56.dp),
            onClick = {
                if (isPlaying) onPause() else onPlay()
            },
        ) {
            if (isPlaying) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Default.Pause,
                    contentDescription = stringResource(R.string.pause)
                )
            } else {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = stringResource(R.string.play),
                )

            }
        }
        IconButton(
            onClick = onNext,
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.SkipNext,
                contentDescription = stringResource(R.string.next)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconToggleButton(
            checked = isFavorite,
            onCheckedChange = onFavoriteChange,
        ) {
            if (isFavorite) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(R.string.is_favorite)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.is_not_favorite)
                )
            }
        }
    }
}
