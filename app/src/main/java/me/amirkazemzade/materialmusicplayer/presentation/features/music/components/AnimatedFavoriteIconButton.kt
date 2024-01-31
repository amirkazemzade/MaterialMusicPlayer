package me.amirkazemzade.materialmusicplayer.presentation.features.music.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.R

@Composable
fun AnimatedFavoriteIconButton(
    isFavorite: Boolean,
    onToggleFavorite: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    val size = if (isFavorite) 28.dp else 24.dp
    val animatedSize = animateDpAsState(
        targetValue = size,
        label = "favorite_icon_button_transformation",
        animationSpec = spring(
            visibilityThreshold = Dp.VisibilityThreshold,
            stiffness = Spring.StiffnessMediumLow,
            dampingRatio = Spring.DampingRatioHighBouncy,
        )
    )

    IconToggleButton(
        modifier = modifier,
        checked = isFavorite,
        onCheckedChange = onToggleFavorite,
    ) {

        if (isFavorite) {
            Icon(
                modifier = Modifier.size(animatedSize.value),
                imageVector = Icons.Filled.Favorite,
                contentDescription = stringResource(R.string.is_favorite)
            )
        } else {
            Icon(
                modifier = Modifier.size(animatedSize.value),
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(R.string.is_not_favorite)
            )
        }
    }
}
