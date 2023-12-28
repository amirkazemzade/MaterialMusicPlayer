package me.amirkazemzade.materialmusicplayer.presentation.common.extensions

import kotlin.time.Duration.Companion.milliseconds

fun Long.formatToMinutesAndSeconds(): String {
    return milliseconds.toComponents(
        action = { minutes, seconds, _ ->
            val minutesString = minutes.toString().padStart(2, '0')
            val secondsString = seconds.toString().padStart(2, '0')
            "$minutesString:$secondsString"
        },
    )
}
