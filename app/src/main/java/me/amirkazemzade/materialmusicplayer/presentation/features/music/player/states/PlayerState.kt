package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states

import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.Util

data class PlayerState(
    val isAvailable: Boolean,
    val isLoading: Boolean = false,
    val isPlaying: Boolean = false,
    val canSkipToNext: Boolean = false,
    val playbackState: Int? = null,
    val mediaMetadata: MediaMetadata? = null,
    val duration: Long? = null,
    val currentPosition: Long? = null,
    val isCurrentMediaItemSeekable: Boolean = false,
    val shuffleModeEnabled: Boolean = false,
) {
    companion object {
        fun fromPlayer(player: Player?): PlayerState {
            val mediaMetadata = player?.mediaMetadata
            val isPlaying = player?.isPlaying
            val isAvailable =
                player != null && (mediaMetadata != null || isPlaying == true) && !player.currentTimeline.isEmpty

            return PlayerState(
                isAvailable = isAvailable,
                isLoading = player?.isLoading ?: false,
                isPlaying = !Util.shouldShowPlayButton(player),
                canSkipToNext = player?.hasNextMediaItem() ?: false,
                playbackState = player?.playbackState,
                mediaMetadata = mediaMetadata,
                duration = player?.duration,
                currentPosition = player?.currentPosition,
                isCurrentMediaItemSeekable = player?.isCurrentMediaItemSeekable ?: false,
                shuffleModeEnabled = player?.shuffleModeEnabled ?: false,
            )
        }
    }
}