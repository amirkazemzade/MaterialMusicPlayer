package me.amirkazemzade.materialmusicplayer.presentation.common.extensions

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun <T> MutableList<T>.repositionElement(fromIndex: Int, toIndex: Int) {
    val repositioningItem = removeAt(index = fromIndex)
    add(index = toIndex, element = repositioningItem)
}

fun <T> ImmutableList<T>.withRepositionedElement(
    fromIndex: Int,
    toIndex: Int,
): ImmutableList<T> {
    return toMutableList()
        .apply { repositionElement(fromIndex, toIndex) }
        .toImmutableList()
}