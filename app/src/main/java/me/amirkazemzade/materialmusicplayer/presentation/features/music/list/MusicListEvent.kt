package me.amirkazemzade.materialmusicplayer.presentation.features.music.list

import androidx.media3.session.MediaController
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.domain.model.SortType

sealed class MusicListEvent {
    data object Retry : MusicListEvent()
    data class SortByType(val sortType: SortType) : MusicListEvent()
    data class SortByOrder(val sortOrder: SortOrder) : MusicListEvent()
    data class Search(val query: String) : MusicListEvent()
    data class Play(
        val index: Int,
        val mediaController: MediaController,
    ) : MusicListEvent()
}