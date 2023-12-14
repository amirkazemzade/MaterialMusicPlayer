package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states

import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player

data class PlayerState(
    val isAvailable: Boolean,
    val isPlaying: Boolean = false,
    val playbackState: Int? = null,
    val mediaMetadata: MediaMetadata? = null,
    val duration: Long? = null,
    val currentPosition: Long? = null,
) {
    companion object {
        fun fromPlayer(player: Player?): PlayerState {
            val mediaMetadata = player?.mediaMetadata
            val isPlaying = player?.isPlaying
            val isAvailable =
                player != null && (mediaMetadata != null || isPlaying == true) && !player.currentTimeline.isEmpty

            return PlayerState(
                isAvailable = isAvailable,
                playbackState = player?.playbackState,
                isPlaying = player?.isPlaying ?: false,
                mediaMetadata = mediaMetadata,
                duration = player?.duration,
                currentPosition = player?.currentPosition,
            )
        }
    }
}