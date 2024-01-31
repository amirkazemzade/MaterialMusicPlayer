package me.amirkazemzade.materialmusicplayer.presentation.common.modifiers

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.highlight(): Modifier {
    return this.background(
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
    )
}
