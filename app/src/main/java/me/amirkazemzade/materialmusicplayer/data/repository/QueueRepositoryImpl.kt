package me.amirkazemzade.materialmusicplayer.data.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.amirkazemzade.materialmusicplayer.data.db.dao.QueueDao
import me.amirkazemzade.materialmusicplayer.data.mappers.mapToQueueItemsWithData
import me.amirkazemzade.materialmusicplayer.data.mappers.toQueue
import me.amirkazemzade.materialmusicplayer.data.mappers.toQueueDataWithItems
import me.amirkazemzade.materialmusicplayer.data.mappers.toQueueEntity
import me.amirkazemzade.materialmusicplayer.data.mappers.toQueueItemEntity
import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.model.QueueData
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItem
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithData
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class QueueRepositoryImpl(
    private val queueDao: QueueDao,
) : QueueRepository {
    override suspend fun getQueue(): Queue = queueDao.getQueueWithMusics().toQueue()

    override fun getQueueItemsFlow(): Flow<ImmutableList<QueueItemWithData>> =
        queueDao
            .getQueueMusicItemsAsFlow()
            .map { items -> items.mapToQueueItemsWithData() }

    override suspend fun setQueue(queue: Queue) = queueDao.setQueue(queue.toQueueDataWithItems())

    override suspend fun clearQueue() = queueDao.deleteQueue()

    override suspend fun updateQueueData(data: QueueData) =
        queueDao.upsertQueueData(data.toQueueEntity())

    override suspend fun addItemToQueue(musicId: Long, order: Int) =
        queueDao.addItemToQueue(musicId, order)

    override suspend fun addItemsToQueue(musicIds: List<Long>, startingOrder: Int) =
        queueDao.addItemsToQueue(musicIds, startingOrder)

    override suspend fun addItemToEndOfQueue(musicId: Long) =
        queueDao.addItemToEndOfQueue(musicId)

    override suspend fun addItemsToEndOfQueue(musicIds: List<Long>) =
        queueDao.addItemsToEndOfQueue(musicIds)

    override suspend fun removeItemFromQueue(item: QueueItem) =
        queueDao.removeItemFromQueue(item.toQueueItemEntity())

    override suspend fun removeItemsFromQueue(items: List<QueueItem>) =
        queueDao.removeItemsFromQueue(items.map { item -> item.toQueueItemEntity() })

    override suspend fun reorderItemInQueue(id: Long, newOrder: Int) =
        queueDao.reorderItemInQueue(id, newOrder)
}