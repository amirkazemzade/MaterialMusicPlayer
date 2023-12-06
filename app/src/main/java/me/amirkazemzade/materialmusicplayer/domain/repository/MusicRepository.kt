package me.amirkazemzade.materialmusicplayer.domain.repository

import kotlinx.collections.immutable.ImmutableList
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile

interface MusicRepository {
    suspend fun getMusicList(sortOrder: String? = null): ImmutableList<MusicFile>
}
