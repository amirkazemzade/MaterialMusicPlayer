package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states

import androidx.compose.runtime.Immutable
import androidx.media3.common.Player

@Immutable
data class TimelineState(
    val duration: Long? = null,
    val currentPosition: Long? = null,
)

fun Player?.toTimelineState(): TimelineState {
    return TimelineState(
        duration = this?.duration,
        currentPosition = this?.currentPosition,
    )
}
