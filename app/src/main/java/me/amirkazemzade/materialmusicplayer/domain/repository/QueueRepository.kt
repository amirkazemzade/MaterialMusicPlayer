package me.amirkazemzade.materialmusicplayer.domain.repository

import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.model.QueueData
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItem
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithData

interface QueueRepository {

    suspend fun getQueue(): Queue

    fun getQueueItemsFlow(): Flow<List<QueueItemWithData>>

    suspend fun setQueue(queue: Queue)

    suspend fun clearQueue()

    suspend fun updateQueueData(data: QueueData)

    suspend fun addItemToQueue(musicId: Long, order: Int)

    suspend fun addItemsToQueue(musicIds: List<Long>, startingOrder: Int)

    suspend fun addItemToEndOfQueue(musicId: Long)

    suspend fun addItemsToEndOfQueue(musicIds: List<Long>)

    suspend fun removeItemFromQueue(item: QueueItem)

    suspend fun removeItemsFromQueue(items: List<QueueItem>)

    suspend fun reorderItemInQueue(id: Long, newOrder: Int)
}
