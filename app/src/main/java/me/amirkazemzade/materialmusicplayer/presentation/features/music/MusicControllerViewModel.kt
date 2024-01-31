package me.amirkazemzade.materialmusicplayer.presentation.features.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.amirkazemzade.materialmusicplayer.data.MusicPlayerController
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicPlayerControllerUseCase
import me.amirkazemzade.materialmusicplayer.presentation.common.defaults.MaterialMusicPlayerDefaults
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.events.PlayerEvent
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.PlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.TimelineState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.toPlayerState
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.toTimelineState

class MusicControllerViewModel(
    getMusicPlayerControllerUseCase: GetMusicPlayerControllerUseCase,
) : ViewModel() {
    private val musicPlayerControllerState = getMusicPlayerControllerUseCase(viewModelScope)

    private val musicPlayerController: MusicPlayerController?
        get() = musicPlayerControllerState.value.data

    private var _playerState = MutableStateFlow(PlayerState(isAvailable = false))
    val playerState: StateFlow<PlayerState>
        get() = _playerState.asStateFlow()

    private val _timelineState = MutableStateFlow(TimelineState())
    val timelineState: StateFlow<TimelineState>
        get() = _timelineState

    init {
        playerStateUpdater()
        timelineStateUpdater()
    }

    private fun playerStateUpdater() {
        viewModelScope.launch {
            musicPlayerControllerState.onEach { mediaControllerStatus ->
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
            musicPlayerControllerState.onEach { mediaControllerStatus ->
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
                            delay(MaterialMusicPlayerDefaults.SCREEN_UPDATE_INTERVAL_MS)
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
        musicPlayerController?.release()
        super.onCleared()
    }

    fun onEvent(event: PlayerEvent) {
        val controller = musicPlayerController
        if (controller != null) {
            when (event) {
                PlayerEvent.Play -> controller.onPlay()
                PlayerEvent.Pause -> controller.onPause()
                PlayerEvent.Next -> controller.onNext()
                PlayerEvent.Previous -> controller.onPrevious()
                is PlayerEvent.SeekTo -> controller.onSeekTo(event.positionMs)
                is PlayerEvent.ShuffleChange -> controller.onShuffleChange(event.shuffleEnable)
                is PlayerEvent.RepeatModeChange -> controller.onRepeatModeChange(event.repeatMode)
            }
        }
    }
}
