package me.amirkazemzade.materialmusicplayer.data.source.music

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import me.amirkazemzade.materialmusicplayer.data.db.dao.MusicDao
import me.amirkazemzade.materialmusicplayer.data.db.dao.VersionDao
import me.amirkazemzade.materialmusicplayer.data.db.entities.music.MusicEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.version.VersionEntity
import me.amirkazemzade.materialmusicplayer.data.mappers.toMusicEntity
import me.amirkazemzade.materialmusicplayer.data.mappers.toMusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.StatusGeneric
import me.amirkazemzade.materialmusicplayer.domain.source.CacheMusicSource

class CacheMusicSourceImp(
    private val versionDao: VersionDao,
    private val musicDao: MusicDao,
) : CacheMusicSource {
    override suspend fun getVersion(): String? = versionDao.getVersion()?.version

    override suspend fun setVersionAndGeneration(version: String, generation: Long?) =
        versionDao.setVersion(VersionEntity(version = version, generation = generation))

    override suspend fun getGeneration(): Long? = versionDao.getVersion()?.generation

    override fun getMusicListOrderedByDateAdded(ascending: Boolean): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> =
        getMusicList { musicDao.getMusicsOrderedByDateAdded(ascending) }

    override fun getMusicListOrderedByTitle(ascending: Boolean): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> =
        getMusicList { musicDao.getMusicsOrderedByTitle(ascending) }

    override fun getMusicListOrderedByArtist(ascending: Boolean): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> =
        getMusicList { musicDao.getMusicsOrderedByArtist(ascending) }

    private fun getMusicList(musics: () -> Flow<List<MusicEntity>>): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> =
        flow {
            emit(StatusGeneric.Loading())
            musics()
                .onEach { musics ->
                    emit(
                        StatusGeneric.Success(
                            data = musics.map { music -> music.toMusicFile() }.toImmutableList(),
                            partialMessage = 0,
                        ),
                    )
                }.collect()
        }

    override suspend fun insertMusicList(musics: List<MusicFile>) {
        musicDao.upsertMusics(musics.map { it.toMusicEntity() })
    }
}