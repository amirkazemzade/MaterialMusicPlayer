package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states

import androidx.compose.runtime.Immutable
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.Util

@Immutable
data class PlayerState(
    val isAvailable: Boolean,
    val isLoading: Boolean = false,
    val isPlaying: Boolean = false,
    val canSkipToNext: Boolean = false,
    val playbackState: Int? = null,
    val mediaMetadata: MediaMetadata? = null,
    val isCurrentMediaItemSeekable: Boolean = false,
    val shuffleModeEnabled: Boolean = false,
)

fun Player?.toPlayerState(): PlayerState {
    val mediaMetadata = this?.mediaMetadata
    val isPlaying = this?.isPlaying
    val isAvailable =
        this != null && (mediaMetadata != null || isPlaying == true) && !this.currentTimeline.isEmpty

    return PlayerState(
        isAvailable = isAvailable,
        isLoading = this?.isLoading ?: false,
        isPlaying = !Util.shouldShowPlayButton(this),
        canSkipToNext = this?.hasNextMediaItem() ?: false,
        playbackState = this?.playbackState,
        mediaMetadata = mediaMetadata,
        isCurrentMediaItemSeekable = this?.isCurrentMediaItemSeekable ?: false,
        shuffleModeEnabled = this?.shuffleModeEnabled ?: false,
    )
}