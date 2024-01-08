package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.model.QueueItem
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class RemoveItemsFromQueueUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(items: List<QueueItem>) {
        repository.removeItemsFromQueue(items)
    }
}
