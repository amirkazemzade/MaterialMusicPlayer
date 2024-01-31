package me.amirkazemzade.materialmusicplayer.presentation.common.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CenteredNote(
    note: String,
    modifier: Modifier = Modifier,
) {
    Center(modifier = modifier) {
        Text(text = note)
    }
}
