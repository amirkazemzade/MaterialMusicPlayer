package me.amirkazemzade.materialmusicplayer.domain.strategies

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.amirkazemzade.materialmusicplayer.domain.model.StatusGeneric

object CachingStrategy {
    /**
     * Emits requested data in the flow based on caching strategy.
     */
    suspend fun <T, S> getValue(
        getCacheVersion: suspend () -> String?,
        getCacheGeneration: suspend () -> Long?,
        getCachedMusicList: () -> Flow<StatusGeneric<T, S>>,
        getRemoteVersion: suspend () -> String,
        getRemoteGeneration: suspend () -> Long?,
        getRemoteMusicList: () -> Flow<StatusGeneric<T, S>>,
        updateCache: suspend (version: String, generation: Long?, data: T) -> Unit,
    ): Flow<StatusGeneric<T, S>> {
        return coroutineScope {
            val cacheVersionDeferred = async { getCacheVersion() }
            val cacheGenerationDeferred = async { getCacheGeneration() }

            val remoteVersionDeferred = async { getRemoteVersion() }
            val remoteGenerationDeferred = async { getRemoteGeneration() }

            val remoteVersion = remoteVersionDeferred.await()
            val cacheVersion = cacheVersionDeferred.await()
            val (cacheGeneration, remoteGeneration) = awaitAll(
                cacheGenerationDeferred,
                remoteGenerationDeferred,
            )

            if (cacheVersion == null) {
                return@coroutineScope getRemoteMusicList().onEach { status ->
                    if (status is StatusGeneric.Success) {
                        updateCache(remoteVersion, remoteGeneration, status.data)
                    }
                }

            }
            if (cacheVersion == remoteVersion) {
                if (cacheGeneration == remoteGeneration) {
                    return@coroutineScope getCachedMusicList()
                }
            }

            return@coroutineScope handleStatus(
                cacheFlow = getCachedMusicList(),
                remoteFlow = getRemoteMusicList(),
                updateCache = { data ->
                    updateCache(remoteVersion, remoteGeneration, data)
                },
            )
        }
    }

    /**
     *  Handles status of two flows ([cacheFlow] and [remoteFlow]) of type [StatusGeneric] with caching strategy.
     *  Initially returns [StatusGeneric.Loading] status.
     *  When cache value is ready before the remote value, emits the cached value.
     *  At the end When remote value gets ready, calls [updateCache] asynchronously to update the cache source and then returns remote value
     */
    private fun <T, S> handleStatus(
        cacheFlow: Flow<StatusGeneric<T, S>>,
        remoteFlow: Flow<StatusGeneric<T, S>>,
        updateCache: suspend (T) -> Unit,
    ): Flow<StatusGeneric<T, S>> = combine(
        cacheFlow,
        remoteFlow,
    ) { cache, remote ->
        coroutineScope {
            if (remote is StatusGeneric.Success || remote is StatusGeneric.Error) {
                if (remote is StatusGeneric.Success && cache is StatusGeneric.Success && remote.data != cache.data) {
                    launch {
                        updateCache(remote.data)
                    }
                }
                return@coroutineScope remote
            }
            if (cache is StatusGeneric.Success) {
                return@coroutineScope cache
            }
            StatusGeneric.Loading()
        }
    }
}