package me.amirkazemzade.materialmusicplayer.presentation.features.music

import androidx.lifecycle.ViewModel
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMediaControllerUseCase

class MusicViewModel(
    getMediaControllerUseCase: GetMediaControllerUseCase,
) : ViewModel() {
    val mediaControllerState = getMediaControllerUseCase()

    override fun onCleared() {
        mediaControllerState.value.data?.release()
        super.onCleared()
    }
}
