package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class GetQueueUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(): Queue = repository.getQueue()
}