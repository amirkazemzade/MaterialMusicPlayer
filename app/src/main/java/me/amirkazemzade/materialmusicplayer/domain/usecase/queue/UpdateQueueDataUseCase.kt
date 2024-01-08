package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.model.QueueData
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class UpdateQueueDataUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(data: QueueData) = repository.updateQueueData(data)
}