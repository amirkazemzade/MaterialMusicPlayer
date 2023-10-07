package me.amirkazemzade.materialmusicplayer.features.musiclist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicListViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<MusicListState> = MutableStateFlow(MusicListState())

    val stateFlow: StateFlow<MusicListState> = _stateFlow.asStateFlow()

}