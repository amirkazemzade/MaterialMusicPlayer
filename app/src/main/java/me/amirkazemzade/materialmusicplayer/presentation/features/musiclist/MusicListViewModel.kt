package me.amirkazemzade.materialmusicplayer.presentation.features.musiclist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.util.encodeBase64
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.amirkazemzade.materialmusicplayer.common.Status
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase
import me.amirkazemzade.materialmusicplayer.presentation.navigation.NavTarget
import me.amirkazemzade.materialmusicplayer.presentation.navigation.Navigator

class MusicListViewModel(
    private val getMusicListUseCase: GetMusicListUseCase,
    private val navigator: Navigator
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

    fun navigateToMusicPlayer(musicFile: MusicFile) {
        val encodedMusicUri = musicFile.uri.toString().encodeBase64()
        navigator.navigateTo(NavTarget.MusicPlayer(encodedMusicUri))
    }
}
