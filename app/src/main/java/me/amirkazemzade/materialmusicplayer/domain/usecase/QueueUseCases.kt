package me.amirkazemzade.materialmusicplayer.domain.usecase

import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.AddItemToEndUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.AddItemToNextUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.AddItemsToEndUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.AddItemsToNextUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.ClearQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.GetQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.RemoveItemFromQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.RemoveItemsFromQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.ReorderItemInQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.SetQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.UpdateQueueDataUseCase

class QueueUseCases(repository: QueueRepository) {
    val getQueueUseCase = GetQueueUseCase(repository)
    val setQueueUseCase = SetQueueUseCase(repository)
    val clearQueueUseCase = ClearQueueUseCase(repository)
    val updateQueueDataUseCase = UpdateQueueDataUseCase(repository)
    val addItemToNextUseCase = AddItemToNextUseCase(repository)
    val addItemsToNextUseCase = AddItemsToNextUseCase(repository)
    val addItemToEndUseCase = AddItemToEndUseCase(repository)
    val addItemsToEndUseCase = AddItemsToEndUseCase(repository)
    val removeItemFromQueueUseCase = RemoveItemFromQueueUseCase(repository)
    val removeItemsFromQueueUseCase = RemoveItemsFromQueueUseCase(repository)
    val reorderItemInQueueUseCase = ReorderItemInQueueUseCase(repository)
}