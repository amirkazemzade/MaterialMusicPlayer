package me.amirkazemzade.materialmusicplayer.presentation.features.music.list

import kotlinx.collections.immutable.ImmutableList
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.domain.model.SortType

sealed class MusicListState(open val sortType: SortType, open val sortOrder: SortOrder) {
    data class Loading(
        override val sortType: SortType = SortType.DATE_ADDED,
        override val sortOrder: SortOrder = SortOrder.DESC,
    ) : MusicListState(sortType, sortOrder)

    data class Error(
        override val sortType: SortType = SortType.DATE_ADDED,
        override val sortOrder: SortOrder = SortOrder.DESC,
        val message: String,
    ) :
        MusicListState(sortType, sortOrder)

    data class Success(
        override val sortType: SortType = SortType.DATE_ADDED,
        override val sortOrder: SortOrder = SortOrder.DESC,
        val musics: ImmutableList<MusicFile>,
        val errorCount: Int = 0,
        val partialLoading: Boolean = false,
    ) :
        MusicListState(sortType, sortOrder)
}