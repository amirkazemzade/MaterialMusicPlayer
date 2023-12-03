package me.amirkazemzade.materialmusicplayer.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.amirkazemzade.materialmusicplayer.presentation.features.music.MusicScreen
import org.koin.compose.koinInject

@Composable
fun NavigationComponent(
    navController: NavHostController,
    navigator: Navigator = koinInject()
) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach {
            Log.d("navError", it.label)
            navController.navigate(it.label) {
                popUpTo(it.label)
            }
        }.launchIn(this)
    }

    // Navigation Directions
    NavHost(
        navController = navController,
        startDestination = NavTarget.Music.label
    ) {
        composable(ModuleRoutes.MusicList.label) {
            MusicScreen()
        }
    }
}
