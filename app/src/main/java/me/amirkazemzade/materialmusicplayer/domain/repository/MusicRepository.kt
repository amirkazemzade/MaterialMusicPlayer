package me.amirkazemzade.materialmusicplayer.domain.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.StatusGeneric

interface MusicRepository {
    fun getMusicList(
        sortOrder: String? = null,
        ascending: Boolean = true,
    ): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>>

    fun getMusicListOrderedByDateAdded(
        ascending: Boolean = true,
    ): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>>

    fun getMusicListOrderedByTitle(
        ascending: Boolean = true,
    ): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>>

    fun getMusicListOrderedByArtist(
        ascending: Boolean = true,
    ): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>>
}
