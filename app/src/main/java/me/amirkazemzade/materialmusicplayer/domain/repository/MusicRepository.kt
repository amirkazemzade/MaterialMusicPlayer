package me.amirkazemzade.materialmusicplayer.domain.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.StatusGeneric

interface MusicRepository {
    suspend fun getMusicListOrderedByDateAdded(
        ascending: Boolean = true,
    ): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>>

    suspend fun getMusicListOrderedByTitle(
        ascending: Boolean = true,
    ): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>>

    suspend fun getMusicListOrderedByArtist(
        ascending: Boolean = true,
    ): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>>
}
