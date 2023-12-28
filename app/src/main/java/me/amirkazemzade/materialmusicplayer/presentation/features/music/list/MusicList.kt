package me.amirkazemzade.materialmusicplayer.presentation.features.music.list

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.presentation.common.ErrorBox
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.components.MusicListContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun MusicList(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: MusicListViewModel = koinViewModel(),
) {
    val mediaControllerState = viewModel.mediaControllerState.collectAsStateWithLifecycle()
    val musicListState = viewModel.state.collectAsStateWithLifecycle()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        when (val musicList = musicListState.value) {
            is MusicListState.Error -> ErrorBox(
                modifier = Modifier.padding(contentPadding),
                message = musicList.message,
                onRetry = {
                    viewModel.onEvent(MusicListEvent.Retry)
                },
            )

            is MusicListState.Loading ->
                CircularProgressIndicator(
                    modifier = Modifier.padding(contentPadding),
                )

            is MusicListState.Success -> {
                val context = LocalContext.current
                LaunchedEffect(key1 = musicList.errorCount) {
                    if (musicList.errorCount > 0) {
                        Toast.makeText(
                            context,
                            context.getString(
                                R.string.musics_list_partial_error_message,
                                musicList.errorCount
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                if (musicList.musics.isEmpty()) {
                    NoMusicFound(
                        modifier = Modifier.padding(contentPadding),
                    )
                } else {
                    MusicListContent(
                        contentPadding = contentPadding,
                        partialLoading = musicList.partialLoading,
                        musics = musicList.musics,
                        sortOrder = musicList.sortOrder,
                        sortType = musicList.sortType,
                        onSortByOrder = { sortOrder ->
                            viewModel.onEvent(
                                MusicListEvent.SortByOrder(
                                    sortOrder
                                )
                            )
                        },
                        onSortByType = { sortType ->
                            viewModel.onEvent(
                                MusicListEvent.SortByType(
                                    sortType
                                )
                            )
                        },
                        onItemClick = { index ->
                            if (mediaControllerState.value is Status.Success) {
                                viewModel.onEvent(
                                    MusicListEvent.Play(
                                        index = index,
                                        mediaController = mediaControllerState.value.data!!
                                    )
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}


@Composable
private fun NoMusicFound(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.no_music_found))
    }
}
