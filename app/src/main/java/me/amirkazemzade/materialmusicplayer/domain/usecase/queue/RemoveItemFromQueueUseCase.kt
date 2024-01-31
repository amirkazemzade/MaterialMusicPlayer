package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class RemoveItemFromQueueUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(id: Long) {
        repository.removeItemFromQueue(id)
    }
}
