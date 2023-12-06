package me.amirkazemzade.materialmusicplayer.presentation.features.music

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.presentation.common.FullScreenLoading
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.MusicList
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.PlayerBottomSheetScaffold
import org.koin.compose.koinInject

@Composable
fun MusicScreen(
    modifier: Modifier = Modifier,
    musicViewModel: MusicViewModel = koinInject(),
) {
    val mediaControllerState = musicViewModel.mediaControllerState.collectAsStateWithLifecycle()
    when (mediaControllerState.value) {
        is Status.Loading -> FullScreenLoading()
        is Status.Success -> {
            PlayerBottomSheetScaffold(
                modifier = modifier,
            ) { innerPadding ->
                MusicList(
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }

        is Status.Error -> {}
    }
}
