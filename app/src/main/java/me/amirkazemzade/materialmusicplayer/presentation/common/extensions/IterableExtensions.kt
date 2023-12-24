package me.amirkazemzade.materialmusicplayer.presentation.common.extensions

fun <T> Iterable<T>.reorder(index: Int): List<T> {
    val list = this.toList()
    val startingElements = list.subList(index, list.size)
    val endingElements = list.subList(0, index)
    return startingElements + endingElements
}