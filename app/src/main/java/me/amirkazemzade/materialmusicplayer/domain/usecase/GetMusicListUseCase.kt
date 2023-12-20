package me.amirkazemzade.materialmusicplayer.domain.usecase

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.amirkazemzade.materialmusicplayer.domain.Constants.DEFAULT_ERROR
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository
import java.io.IOException

class GetMusicListUseCase(
    private val repository: MusicRepository,
) {
    operator fun invoke(sortOrder: String? = null): Flow<Status<ImmutableList<MusicFile>>> =
        flow {
            try {
                emit(Status.Loading())
                val musicList = repository.getMusicList(sortOrder)
                emit(Status.Success(musicList))
            } catch (e: IOException) {
                emit(Status.Error(e.localizedMessage ?: DEFAULT_ERROR))
            } catch (e: Exception) {
                emit(Status.Error(e.localizedMessage ?: DEFAULT_ERROR))
            }
        }
}
