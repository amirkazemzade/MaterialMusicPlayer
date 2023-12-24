package me.amirkazemzade.materialmusicplayer.presentation.features.music

sealed interface MusicEvent {
    data object Play : MusicEvent
    data object Pause : MusicEvent
    data object Next : MusicEvent
    data object Previous : MusicEvent
    data class SeekTo(val positionMs: Long) : MusicEvent
    data class ShuffleChange(val shuffleEnable: Boolean) : MusicEvent
}