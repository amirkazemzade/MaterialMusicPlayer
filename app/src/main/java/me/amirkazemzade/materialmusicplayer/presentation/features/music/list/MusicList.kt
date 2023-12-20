package me.amirkazemzade.materialmusicplayer.presentation.features.music.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.toImmutableList
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.components.MusicListContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun MusicList(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: MusicListViewModel = koinViewModel(),
) {
    val mediaControllerState = viewModel.mediaControllerState.collectAsStateWithLifecycle()
    val musicListState = viewModel.state.value
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        when (musicListState) {
            is Status.Error -> Text(text = musicListState.message!!)
            is Status.Loading -> CircularProgressIndicator()
            is Status.Success -> {
                if (musicListState.data.isNullOrEmpty()) {
                    NoMusicFound()
                } else {
                    MusicListContent(
                        contentPadding = contentPadding,
                        musics = musicListState.data.toImmutableList(),
                        onItemClick = { index ->
                            if (mediaControllerState.value is Status.Success) {
                                viewModel.playMusic(index, mediaControllerState.value.data!!)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun NoMusicFound() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "No Music Found!")
    }
}
