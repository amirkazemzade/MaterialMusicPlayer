package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class RemoveItemsFromQueueUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(ids: List<Long>) {
        repository.removeItemsFromQueue(ids)
    }
}
