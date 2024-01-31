package me.amirkazemzade.materialmusicplayer.presentation.features.music.list.events

import me.amirkazemzade.materialmusicplayer.domain.model.DefaultSortType
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder

sealed class MusicListEvent {
    data object Retry : MusicListEvent()
    data class SortByType(val sortType: DefaultSortType) : MusicListEvent()
    data class SortByOrder(val sortOrder: SortOrder) : MusicListEvent()
    data class Search(val query: String) : MusicListEvent()
    data class Play(val index: Int) : MusicListEvent()
}