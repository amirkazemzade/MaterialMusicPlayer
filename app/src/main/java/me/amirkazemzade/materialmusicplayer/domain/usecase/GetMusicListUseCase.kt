package me.amirkazemzade.materialmusicplayer.domain.usecase

import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.amirkazemzade.materialmusicplayer.domain.Constants.DEFAULT_ERROR
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository

class GetMusicListUseCase(
    private val repository: MusicRepository
) {
    operator fun invoke(
        sortOrder: String? = null
    ): Flow<Status<List<MusicFile>>> = flow {
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
