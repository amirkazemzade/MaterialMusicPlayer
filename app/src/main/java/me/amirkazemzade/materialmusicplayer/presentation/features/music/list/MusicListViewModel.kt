package me.amirkazemzade.materialmusicplayer.presentation.features.music.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMediaControllerUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase
import me.amirkazemzade.materialmusicplayer.presentation.common.reorder

class MusicListViewModel(
    private val getMusicListUseCase: GetMusicListUseCase,
    getMediaControllerUseCase: GetMediaControllerUseCase,
) : ViewModel() {
    val mediaControllerState = getMediaControllerUseCase()

    private var _state = mutableStateOf<Status<ImmutableList<MusicFile>>>(Status.Loading())
    val state: State<Status<ImmutableList<MusicFile>>> = _state

    init {
        getMusicList()
    }

    // TODO: implement pull to refresh using this function to preform action
    private fun getMusicList() {
        getMusicListUseCase()
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    fun playMusic(
        index: Int,
        mediaController: MediaController,
    ) {
        val playList =
            state.value.data
                ?.reorder(index)
                ?.map { MediaItem.fromUri(it.uri) }
        playList?.let {
            mediaController.clearMediaItems()
            mediaController.setMediaItems(playList)
            mediaController.prepare()
            mediaController.play()
        }
    }

    override fun onCleared() {
        mediaControllerState.value.data?.release()
        super.onCleared()
    }
}
