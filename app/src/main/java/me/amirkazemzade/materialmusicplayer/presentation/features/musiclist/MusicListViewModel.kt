package me.amirkazemzade.materialmusicplayer.presentation.features.musiclist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.amirkazemzade.materialmusicplayer.common.Status
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase

class MusicListViewModel(
    private val getMusicListUseCase: GetMusicListUseCase
) : ViewModel() {

    private var _state = mutableStateOf<Status<List<MusicFile>>>(Status.Loading())
    val state: State<Status<List<MusicFile>>> = _state

    init {
        getMusicList()
    }

    fun getMusicList() {
        getMusicListUseCase()
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }
}
