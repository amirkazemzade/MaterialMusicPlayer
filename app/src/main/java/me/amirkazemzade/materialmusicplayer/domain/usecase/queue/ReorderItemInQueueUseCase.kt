package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class ReorderItemInQueueUseCase(val repository: QueueRepository) {

    suspend operator fun invoke(id: Long, newOrder: Int) =
        repository.reorderItemInQueue(id, newOrder)
}
