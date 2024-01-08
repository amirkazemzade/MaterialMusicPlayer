package me.amirkazemzade.materialmusicplayer.domain.usecase

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.domain.model.SortType
import me.amirkazemzade.materialmusicplayer.domain.model.StatusCore
import me.amirkazemzade.materialmusicplayer.domain.model.StatusGeneric
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository

class GetMusicListUseCase(
    private val repository: MusicRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(
        sortType: SortType,
        sortOrder: SortOrder,
    ): Flow<StatusCore<ImmutableList<MusicFile>, String, Int>> = when (sortType) {
        SortType.DATE_ADDED -> repository.getMusicListOrderedByDateAdded(sortOrder.isAscending)
        SortType.TITLE -> repository.getMusicListOrderedByTitle(sortOrder.isAscending)
        SortType.ARTIST -> repository.getMusicListOrderedByArtist(sortOrder.isAscending)
    }.mapLatest<StatusGeneric<ImmutableList<MusicFile>, Int>, StatusCore<ImmutableList<MusicFile>, String, Int>> { value ->
        when (value) {
            is StatusGeneric.Loading -> StatusCore.Loading(
                data = value.data,
            )

            is StatusGeneric.Error -> StatusCore.Error(
                message = "${value.message}",
                data = value.data,
            )

            is StatusGeneric.Success -> StatusCore.Success(
                data = value.data,
                partialMessage = value.partialMessage,
            )
        }
    }.catch {
        emit(StatusCore.Error(message = it.localizedMessage ?: ""))
    }
}
