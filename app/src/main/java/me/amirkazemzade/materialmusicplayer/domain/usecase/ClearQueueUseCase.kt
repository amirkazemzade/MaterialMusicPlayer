package me.amirkazemzade.materialmusicplayer.domain.usecase

import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class ClearQueueUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke() = repository.clearQueue()
}