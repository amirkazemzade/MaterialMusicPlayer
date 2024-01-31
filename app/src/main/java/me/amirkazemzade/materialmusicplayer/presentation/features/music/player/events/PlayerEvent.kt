package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.events

import me.amirkazemzade.materialmusicplayer.domain.model.RepeatMode

sealed interface PlayerEvent {
    data object Play : PlayerEvent
    data object Pause : PlayerEvent
    data object Next : PlayerEvent
    data object Previous : PlayerEvent
    data class SeekTo(val positionMs: Long) : PlayerEvent
    data class ShuffleChange(val shuffleEnable: Boolean) : PlayerEvent
    data class RepeatModeChange(val repeatMode: RepeatMode) : PlayerEvent
}