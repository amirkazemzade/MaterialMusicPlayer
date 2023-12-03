package me.amirkazemzade.materialmusicplayer.presentation.features.music.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.amirkazemzade.materialmusicplayer.common.reorder
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase
import me.amirkazemzade.materialmusicplayer.presentation.navigation.Navigator

class MusicListViewModel(
    private val getMusicListUseCase: GetMusicListUseCase,
    private val navigator: Navigator,
    private val player: Player
) : ViewModel() {

    private var _state = mutableStateOf<Status<List<MusicFile>>>(Status.Loading())
    val state: State<Status<List<MusicFile>>> = _state

    init {
        getMusicList()
    }

    // TODO: implement pull to refresh using this function to preform action
    private fun getMusicList() {
        getMusicListUseCase()
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    fun playMusic(index: Int) {
        val playList = state.value.data
            ?.reorder(index)
            ?.map { MediaItem.fromUri(it.uri) }
        playList?.let {
            player.clearMediaItems()
            player.setMediaItems(playList)
            player.play()
        }
    }
}
