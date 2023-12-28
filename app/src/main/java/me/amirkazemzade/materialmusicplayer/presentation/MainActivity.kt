package me.amirkazemzade.materialmusicplayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import me.amirkazemzade.materialmusicplayer.presentation.common.components.ReadAudioPermissionHandler
import me.amirkazemzade.materialmusicplayer.presentation.navigation.NavigationComponent
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext {
                MaterialMusicPlayerTheme {
                    ReadAudioPermissionHandler {
                        val navController = rememberNavController()
                        NavigationComponent(navController = navController)
                    }
                }
            }
        }
    }
}
