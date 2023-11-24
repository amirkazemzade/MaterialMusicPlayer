package me.amirkazemzade.materialmusicplayer.presentation.features.player

import android.net.Uri
import androidx.compose.runtime.Composable
import io.ktor.util.decodeBase64String
import me.amirkazemzade.materialmusicplayer.presentation.features.player.components.MusicPlayer

@Composable
fun MusicPlayerScreen(encodedMusicUri: String) {
    val musicUri = encodedMusicUri.decodeBase64String()
    MusicPlayer(musicUri = Uri.parse(musicUri))
}
