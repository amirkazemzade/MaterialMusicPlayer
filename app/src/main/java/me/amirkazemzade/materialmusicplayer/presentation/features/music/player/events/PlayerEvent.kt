package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.events

sealed interface PlayerEvent {
    data object Play : PlayerEvent
    data object Pause : PlayerEvent
    data object Next : PlayerEvent
    data object Previous : PlayerEvent
    data class SeekTo(val positionMs: Long) : PlayerEvent
    data class ShuffleChange(val shuffleEnable: Boolean) : PlayerEvent
}