package me.amirkazemzade.materialmusicplayer.presentation.features.music.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.components.MusicListContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun MusicList(
    modifier: Modifier = Modifier,
    viewModel: MusicListViewModel = koinViewModel()
) {
    val state = viewModel.state.value

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        when (state) {
            is Status.Error -> Text(text = state.message!!)
            is Status.Loading -> CircularProgressIndicator()
            is Status.Success -> MusicListContent(
                musics = buildList {
                    // TODO: remove repeat after debugging
                    repeat(2) {
                        addAll(state.data!!)
                    }
                },
                onItemClick = { index ->
                    viewModel.playMusic(index)
                }
            )
        }
    }
}
