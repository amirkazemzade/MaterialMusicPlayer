package me.amirkazemzade.materialmusicplayer.presentation.common.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CenteredCircularLoading(modifier: Modifier = Modifier) {
    Center(modifier = modifier) {
        CircularProgressIndicator()
    }
}
