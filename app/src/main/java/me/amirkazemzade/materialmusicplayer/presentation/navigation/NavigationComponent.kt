package me.amirkazemzade.materialmusicplayer.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.amirkazemzade.materialmusicplayer.presentation.features.musiclist.MusicListScreen
import me.amirkazemzade.materialmusicplayer.presentation.features.player.MusicPlayerScreen
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
        startDestination = NavTarget.MusicList.label
    ) {
        composable(ModuleRoutes.MusicList.label) {
            MusicListScreen()
        }
        val musicUriArg = "musicUri"
        composable(
            "${ModuleRoutes.MusicPlayer.label}/{$musicUriArg}",
            arguments = listOf(navArgument(musicUriArg) { type = NavType.StringType })
        ) { backStackEntry ->
            val musicUri = backStackEntry.arguments?.getString(musicUriArg)
            requireNotNull(musicUri) {
                "musicUri in MusicPlayer is null. Please make sure the musicUri is set!"
            }
            MusicPlayerScreen(musicUri)
        }
    }
}
