package me.amirkazemzade.materialmusicplayer.presentation.features.music

import androidx.lifecycle.ViewModel
import androidx.media3.common.Player
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMediaControllerUseCase
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.MusicEvent

class MusicControllerViewModel(
    getMediaControllerUseCase: GetMediaControllerUseCase,
) : ViewModel() {
    val mediaControllerState = getMediaControllerUseCase()

    private val mediaController
        get() = mediaControllerState.value.data

    override fun onCleared() {
        mediaControllerState.value.data?.release()
        super.onCleared()
    }

    fun onEvent(event: MusicEvent) {
        when (event) {
            MusicEvent.Play -> onPlay()
            MusicEvent.Pause -> onPause()
            MusicEvent.Next -> onNext()
            MusicEvent.Previous -> onPrevious()
            is MusicEvent.SeekTo -> onSeekTo(event.positionMs)
        }
    }

    private fun onPlay() {
        if (mediaController?.isCommandAvailable(Player.COMMAND_PLAY_PAUSE) == true)
            mediaController?.play()
    }

    private fun onPause() {
        if (mediaController?.isCommandAvailable(Player.COMMAND_PLAY_PAUSE) == true)
            mediaController?.pause()
    }

    private fun onNext() {
        if (mediaController?.isCommandAvailable(Player.COMMAND_SEEK_TO_NEXT) == true)
            mediaController?.seekToNext()
    }

    private fun onPrevious() {
        if (mediaController?.isCommandAvailable(Player.COMMAND_SEEK_TO_PREVIOUS) == true)
            mediaController?.seekToPrevious()
    }

    private fun onSeekTo(positionMs: Long) {
        if (mediaController?.isCommandAvailable(Player.COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM) == true)
            mediaController?.seekTo(positionMs)
    }
}