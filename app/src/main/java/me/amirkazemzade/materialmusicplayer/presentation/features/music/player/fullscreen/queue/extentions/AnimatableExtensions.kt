package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.extentions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D

fun Animatable<Float, AnimationVector1D>.updateListDraggableItemBounds(
    index: Int,
    totalItemsCount: Int,
    heightPx: Float,
) {
    val lowerBound = -(index * heightPx)
    val upperBound = (totalItemsCount - index - 1) * heightPx
    updateBounds(lowerBound, upperBound)
}