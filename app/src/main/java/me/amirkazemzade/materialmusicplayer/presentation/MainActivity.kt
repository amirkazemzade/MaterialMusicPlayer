package me.amirkazemzade.materialmusicplayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.amirkazemzade.materialmusicplayer.presentation.common.ReadAudioPermissionHandler
import me.amirkazemzade.materialmusicplayer.presentation.features.musiclist.MusicListScreen
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext {
                MaterialMusicPlayerTheme {
                    ReadAudioPermissionHandler {
                        MusicListScreen()
                    }
                }
            }
        }
    }
}
