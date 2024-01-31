package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.events

import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.domain.model.SortTypeWithCustom

sealed interface MusicQueueListEvent {
    data class Play(val index: Int) : MusicQueueListEvent
    data class Reorder(val id: Long, val newOrder: Int) : MusicQueueListEvent
    data object ShuffleQueue : MusicQueueListEvent
    data class SortByType(val sortType: SortTypeWithCustom) : MusicQueueListEvent
    data class SortByOrder(val sortOrder: SortOrder) : MusicQueueListEvent
}