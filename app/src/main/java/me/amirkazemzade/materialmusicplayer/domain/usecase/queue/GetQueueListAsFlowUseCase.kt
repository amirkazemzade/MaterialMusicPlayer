package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic
import me.amirkazemzade.materialmusicplayer.domain.model.Status
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class GetQueueListAsFlowUseCase(private val repository: QueueRepository) {

    operator fun invoke(): Flow<Status<ImmutableList<QueueItemWithMusic>>> =
        repository.getQueueItemsWithMusicFlow()
}