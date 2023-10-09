package me.amirkazemzade.materialmusicplayer.presentation.features.musiclist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.amirkazemzade.materialmusicplayer.common.Status
import me.amirkazemzade.materialmusicplayer.presentation.features.musiclist.components.MusicList
import org.koin.androidx.compose.koinViewModel

@Composable
fun MusicListScreen(
    modifier: Modifier = Modifier,
    viewModel: MusicListViewModel = koinViewModel()
) {
    val state = viewModel.state.value

    Scaffold(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column {
                when (state) {
                    is Status.Error -> Text(text = state.message!!)
                    is Status.Loading -> CircularProgressIndicator()
                    is Status.Success -> MusicList(
                        musics = buildList {
                            repeat(10) {
                                addAll(
                                    state.data!!

                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
