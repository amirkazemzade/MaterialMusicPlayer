package me.amirkazemzade.materialmusicplayer.common

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun <T> Iterable<T>.reorder(index: Int): List<T> {
    val list = this.toList()
    val startingElements = list.subList(index, list.size)
    val endingElements = list.subList(0, index)
    return startingElements + endingElements
}

fun ByteArray.toImageBitmap(): ImageBitmap =
    BitmapFactory
        .decodeByteArray(this, 0, this.size)
        .asImageBitmap()
