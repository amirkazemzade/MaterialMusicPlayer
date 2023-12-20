package me.amirkazemzade.materialmusicplayer.presentation.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlin.time.Duration.Companion.milliseconds

fun <T> Iterable<T>.reorder(index: Int): List<T> {
    val list = this.toList()
    val startingElements = list.subList(index, list.size)
    val endingElements = list.subList(0, index)
    return startingElements + endingElements
}

fun ByteArray.toImageBitmap(): ImageBitmap = toBitmap().asImageBitmap()

fun ByteArray.toBitmap(): Bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)

fun Long.formatToMinutesAndSeconds(): String {
    return milliseconds.toComponents(
        action = { minutes, seconds, _ ->
            val minutesString = minutes.toString().padStart(2, '0')
            val secondsString = seconds.toString().padStart(2, '0')
            "$minutesString:$secondsString"
        },
    )
}
