package me.amirkazemzade.materialmusicplayer.domain.source

import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile

interface WriteableMusicSource {
    suspend fun setVersionAndGeneration(version: String, generation: Long?)

    suspend fun insertMusicList(musics: List<MusicFile>)
}
