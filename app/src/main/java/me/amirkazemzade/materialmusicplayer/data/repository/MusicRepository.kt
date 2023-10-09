package me.amirkazemzade.materialmusicplayer.data.repository

import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile

interface MusicRepository {
    fun getMusicList(sortOrder: String? = null): List<MusicFile>
}
