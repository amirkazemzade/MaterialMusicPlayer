package me.amirkazemzade.materialmusicplayer.domain.repository

import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile

interface MusicRepository {
    suspend fun getMusicList(sortOrder: String? = null): List<MusicFile>
}
