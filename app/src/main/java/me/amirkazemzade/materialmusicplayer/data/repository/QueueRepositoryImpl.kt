package me.amirkazemzade.materialmusicplayer.data.repository

import me.amirkazemzade.materialmusicplayer.data.db.dao.QueueDao
import me.amirkazemzade.materialmusicplayer.data.mappers.toQueue
import me.amirkazemzade.materialmusicplayer.data.mappers.toQueueWithItems
import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class QueueRepositoryImpl(
    private val queueDao: QueueDao,
) : QueueRepository {
    override suspend fun setQueue(queue: Queue) = queueDao.setQueue(queue.toQueueWithItems())

    override suspend fun getQueue(): Queue = queueDao.getQueue().toQueue()

    override suspend fun clearQueue() = queueDao.deleteQueue()
}