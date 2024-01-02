package me.amirkazemzade.materialmusicplayer.presentation.features.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.common.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMediaControllerUseCase
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.TimelineState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.toPlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.toTimelineState

class MusicControllerViewModel(
    getMediaControllerUseCase: GetMediaControllerUseCase,
) : ViewModel() {
    val mediaControllerState = getMediaControllerUseCase()

    private val mediaController
        get() = mediaControllerState.value.data

    private val _playerState = MutableStateFlow(PlayerState(isAvailable = false))
    val playerState: StateFlow<PlayerState>
        get() = _playerState

    private val _timelineState = MutableStateFlow(TimelineState())
    val timelineState: StateFlow<TimelineState>
        get() = _timelineState

    init {
        playerStateUpdater()
        timelineStateUpdater()
    }

    private fun playerStateUpdater() {
        viewModelScope.launch {
            mediaControllerState.onEach { mediaControllerStatus ->
                val mediaController = mediaControllerStatus.data
                if (mediaController == null) {
                    _playerState.value = PlayerState(isAvailable = false)
                } else {
                    _playerState.value = mediaController.toPlayerState()
                    mediaController.addListener(
                        playerEventListener(
                            onEvents = { player ->
                                _playerState.value = player.toPlayerState()
                            },
                        )
                    )
                }
            }.collect()
        }
    }

    private fun timelineStateUpdater() {
        viewModelScope.launch {
            mediaControllerState.onEach { mediaControllerStatus ->
                val mediaController = mediaControllerStatus.data
                if (mediaController == null) {
                    _timelineState.value = TimelineState()
                } else {
                    mediaController.addListener(
                        playerEventListener(
                            onEvents = { player ->
                                _timelineState.value = player.toTimelineState()
                            },
                        )
                    )
                    withContext(Dispatchers.IO) {
                        while (isActive) {
                            withContext(Dispatchers.Main) {
                                _timelineState.value = mediaController.toTimelineState()
                            }
                            delay(500)
                        }
                    }
                }
            }.collect()
        }
    }

    private fun playerEventListener(onEvents: (player: Player) -> Unit): Player.Listener =
        object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                onEvents(player)
                super.onEvents(player, events)
            }
        }

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
            is MusicEvent.ShuffleChange -> onShuffleChange(event.shuffleEnable)
        }
    }

    private fun onPlay() {
        Util.handlePlayButtonAction(mediaController)
    }

    private fun onPause() {
        Util.handlePauseButtonAction(mediaController)
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

    private fun onShuffleChange(shuffleEnable: Boolean) {
        if (mediaController?.isCommandAvailable(Player.COMMAND_SET_SHUFFLE_MODE) == true)
            mediaController?.shuffleModeEnabled = shuffleEnable
    }
}
