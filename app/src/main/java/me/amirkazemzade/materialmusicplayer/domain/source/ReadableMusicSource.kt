package me.amirkazemzade.materialmusicplayer.domain.source

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.StatusGeneric

interface ReadableMusicSource {
    suspend fun getVersion(): String?

    suspend fun getGeneration(): Long?

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
