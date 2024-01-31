package me.amirkazemzade.materialmusicplayer.domain.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.model.QueueData
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic
import me.amirkazemzade.materialmusicplayer.domain.model.Status

interface QueueRepository {

    suspend fun getQueue(): Queue

    fun getQueueItemsWithMusicFlow(): Flow<Status<ImmutableList<QueueItemWithMusic>>>

    suspend fun setQueue(queue: Queue)

    suspend fun clearQueue()

    suspend fun updateQueueData(data: QueueData)

    suspend fun addItemToQueue(musicId: Long, order: Int)

    suspend fun addItemsToQueue(musicIds: List<Long>, startingOrder: Int)

    suspend fun addItemToEndOfQueue(musicId: Long)

    suspend fun addItemsToEndOfQueue(musicIds: List<Long>)

    suspend fun removeItemFromQueue(id: Long)

    suspend fun removeItemsFromQueue(ids: List<Long>)

    suspend fun reorderItemInQueue(id: Long, newOrder: Int)
}
