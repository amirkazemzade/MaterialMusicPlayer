package me.amirkazemzade.materialmusicplayer.presentation.features.music

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.presentation.common.components.FullScreenLoading
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.MusicList
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.PlayerBottomSheetScaffold
import org.koin.compose.koinInject

@Composable
fun MusicScreen(
    modifier: Modifier = Modifier,
    musicControllerViewModel: MusicControllerViewModel = koinInject(),
) {
    val mediaControllerState =
        musicControllerViewModel.mediaControllerState.collectAsStateWithLifecycle()
    when (mediaControllerState.value) {
        is Status.Loading -> FullScreenLoading()
        is Status.Success -> {
            PlayerBottomSheetScaffold(
                modifier = modifier,
            ) { innerPadding, contentModifier ->
                MusicList(
                    contentPadding = innerPadding,
                    modifier = contentModifier,
                )
            }
        }

        is Status.Error -> {}
    }
}
