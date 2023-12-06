package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.components

import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player

data class PlayerState(
    val isPlaying: Boolean = false,
    val mediaMetadata: MediaMetadata? = null,
    val duration: Long? = null,
    val currentPosition: Long? = null,
)

fun Player.toPlayerState(): PlayerState {
    return PlayerState(
        isPlaying = this.isPlaying,
        mediaMetadata = this.mediaMetadata,
        duration = this.duration,
        currentPosition = this.currentPosition,
    )
}
