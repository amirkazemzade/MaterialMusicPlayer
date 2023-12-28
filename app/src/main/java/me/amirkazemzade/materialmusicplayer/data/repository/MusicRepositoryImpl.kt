package me.amirkazemzade.materialmusicplayer.data.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.StatusGeneric
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository
import me.amirkazemzade.materialmusicplayer.domain.source.CacheMusicSource
import me.amirkazemzade.materialmusicplayer.domain.source.RemoteMusicSource
import me.amirkazemzade.materialmusicplayer.domain.strategies.CachingStrategy

class MusicRepositoryImpl(
    private val cacheSource: CacheMusicSource,
    private val remoteSource: RemoteMusicSource,
) : MusicRepository {

    override suspend fun getMusicListOrderedByDateAdded(ascending: Boolean): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> =
        getMusicList(
            getRemoteMusicList = { remoteSource.getMusicListOrderedByDateAdded(ascending) },
            getCachedMusicList = { cacheSource.getMusicListOrderedByDateAdded(ascending) }
        )

    override suspend fun getMusicListOrderedByTitle(ascending: Boolean): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> =
        getMusicList(
            getRemoteMusicList = { remoteSource.getMusicListOrderedByTitle(ascending) },
            getCachedMusicList = { cacheSource.getMusicListOrderedByTitle(ascending) }
        )

    override suspend fun getMusicListOrderedByArtist(ascending: Boolean): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> =
        getMusicList(
            getRemoteMusicList = { remoteSource.getMusicListOrderedByArtist(ascending) },
            getCachedMusicList = { cacheSource.getMusicListOrderedByArtist(ascending) }
        )

    private suspend fun getMusicList(
        getRemoteMusicList: () -> Flow<StatusGeneric<ImmutableList<MusicFile>, Int>>,
        getCachedMusicList: () -> Flow<StatusGeneric<ImmutableList<MusicFile>, Int>>,
    ): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> {
        return CachingStrategy.getValue(
            getCacheVersion = suspend { cacheSource.getVersion() },
            getCacheGeneration = suspend { cacheSource.getGeneration() },
            getRemoteVersion = suspend { remoteSource.getVersion() },
            getRemoteGeneration = suspend { remoteSource.getGeneration() },
            getRemoteMusicList = getRemoteMusicList,
            getCachedMusicList = getCachedMusicList,
            updateCache = { version, generation, musics ->
                cacheSource.insertMusicList(musics)
                cacheSource.setVersionAndGeneration(version, generation)
            }
        )
    }
}
