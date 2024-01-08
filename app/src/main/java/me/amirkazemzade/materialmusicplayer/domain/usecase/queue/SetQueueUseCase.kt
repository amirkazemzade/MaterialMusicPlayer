package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class SetQueueUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(queue: Queue) = repository.setQueue(queue)
}