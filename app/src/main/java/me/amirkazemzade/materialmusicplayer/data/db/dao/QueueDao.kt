package me.amirkazemzade.materialmusicplayer.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueItemEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueWithItems

@Dao
interface QueueDao {

    @Insert
    suspend fun upsertQueue(queue: QueueEntity): Long

    @Upsert
    suspend fun upsertQueueItems(queueItems: List<QueueItemEntity>)

    @Query("DELETE FROM QueueEntity")
    suspend fun deleteQueue()

    @Query("DELETE FROM QueueItemEntity")
    suspend fun deleteQueueItems()

    @Transaction
    suspend fun setQueue(queueWithItems: QueueWithItems) {
        deleteQueueItems()
        deleteQueue()
        val queueId = upsertQueue(queueWithItems.queue)
        val items = queueWithItems.items.map { item ->
            item.copy(queueId = queueId)
        }
        upsertQueueItems(items)
    }

    @Transaction
    @Query("SELECT * FROM QueueEntity")
    suspend fun getQueue(): QueueWithItems?
}