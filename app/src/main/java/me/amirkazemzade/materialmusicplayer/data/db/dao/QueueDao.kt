package me.amirkazemzade.materialmusicplayer.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.data.db.dto.QueueDataWithItemsDto
import me.amirkazemzade.materialmusicplayer.data.db.dto.QueueItemWithMusicDto
import me.amirkazemzade.materialmusicplayer.data.db.dto.QueueMetadataItemDto
import me.amirkazemzade.materialmusicplayer.data.db.dto.QueueWithMetadataItemsDto
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueDataEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueItemEntity

@Dao
interface QueueDao {
    @Query("SELECT * FROM QueueDataEntity")
    fun getQueueData(): QueueDataEntity?

    @Query("SELECT id FROM QueueItemEntity WHERE `order` = :order")
    suspend fun getQueueItem(order: Int): Long

    @Query(
        """
            SELECT QueueItemEntity.id as id, 
            MusicEntity.id as musicId, 
            QueueItemEntity.`order` as `order`, 
            MusicEntity.uri as uri, 
            MusicEntity.title as title, 
            MusicEntity.artist as artist 
            FROM QueueItemEntity 
            LEFT JOIN MusicEntity 
            WHERE QueueItemEntity.musicId = MusicEntity.id
        """
    )
    fun getQueueMetadataItems(): List<QueueMetadataItemDto>

    @Transaction
    @Query("SELECT * FROM QueueItemEntity")
    fun getQueueItemsWithMusicAsFlow(): Flow<List<QueueItemWithMusicDto>>

    @Transaction
    fun getQueueWithMusics(): QueueWithMetadataItemsDto? {
        val data = getQueueData()
        val items = getQueueMetadataItems()
        if (data == null) return null
        return QueueWithMetadataItemsDto(
            queue = data,
            items = items,
        )
    }

    @Query("SELECT `order` FROM QueueItemEntity WHERE id = :id")
    fun getQueueItemOrder(id: Long): Int

    @Query("SELECT `order` FROM QueueItemEntity ORDER BY `order` DESC LIMIT 1")
    suspend fun getLastOrder(): Int

    @Insert
    suspend fun insertQueueData(data: QueueDataEntity)

    @Insert
    suspend fun insertQueueItem(item: QueueItemEntity)

    @Insert
    suspend fun insertQueueItems(items: List<QueueItemEntity>)

    @Upsert
    suspend fun upsertQueueData(data: QueueDataEntity)

    @Query("DELETE FROM QueueDataEntity")
    suspend fun deleteQueue()

    @Query("DELETE FROM QueueItemEntity")
    suspend fun deleteQueueItems()

    @Delete
    suspend fun deleteQueueItem(queueItem: QueueItemEntity)

    @Query("DELETE FROM QueueItemEntity WHERE id=:id")
    suspend fun deleteQueueItem(id: Long)

    @Transaction
    suspend fun deleteQueueItems(ids: List<Long>) {
        for (id in ids) {
            deleteQueueItem(id)
        }
    }

    @Transaction
    suspend fun setQueue(queueDataWithItemsDto: QueueDataWithItemsDto) {
        deleteQueue()
        deleteQueueItems()

        insertQueueData(queueDataWithItemsDto.data)
        insertQueueItems(queueDataWithItemsDto.items)
    }

    @Query("UPDATE QueueItemEntity SET `order`=:order WHERE id = :id")
    suspend fun updateQueueItem(id: Long, order: Int)

    @Query("UPDATE QueueItemEntity SET `order`=`order` + :shiftLevel WHERE `order` >= :fromOrder")
    fun shiftDownOrders(fromOrder: Int, shiftLevel: Int = 1)

    @Query("UPDATE QueueItemEntity SET `order`=`order` - 1 WHERE `order` >= :fromOrder")
    fun shiftUpOrders(fromOrder: Int)

    @Query("UPDATE QueueItemEntity SET `order`=`order` - 1 WHERE `order` > :fromOrder AND `order` < :toOrder")
    fun shiftUpOrders(fromOrder: Int, toOrder: Int)

    @Transaction
    suspend fun addItemToQueue(musicId: Long, order: Int) {
        shiftDownOrders(order)
        insertQueueItem(QueueItemEntity(musicId = musicId, order = order))
    }

    @Transaction
    suspend fun addItemsToQueue(musicIds: List<Long>, startingOrder: Int) {
        val shiftLevel = musicIds.size
        shiftDownOrders(startingOrder, shiftLevel)
        musicIds.forEachIndexed { index, musicId ->
            val queueItem = QueueItemEntity(musicId = musicId, order = startingOrder + index)
            insertQueueItem(queueItem)
        }
    }

    @Transaction
    suspend fun addItemToEndOfQueue(musicId: Long) {
        insertQueueItem(QueueItemEntity(musicId = musicId, order = getLastOrder() + 1))
    }

    @Transaction
    suspend fun addItemsToEndOfQueue(musicIds: List<Long>) {
        val startingOrder = getLastOrder() + 1
        musicIds.forEachIndexed { index, musicId ->
            val queueItem = QueueItemEntity(musicId = musicId, order = startingOrder + index)
            insertQueueItem(queueItem)
        }
    }

    @Transaction
    suspend fun removeItemFromQueue(id: Long) {
        val order = getQueueItemOrder(id)
        deleteQueueItem(id)
        shiftUpOrders(order)
    }

    @Transaction
    suspend fun removeItemsFromQueue(ids: List<Long>) {
        var fromOrder = getQueueItemOrder(ids.first())
        var toOrder: Int? = null
        ids.forEachIndexed { index, item ->
            if (index != 0) {
                fromOrder = toOrder!!
            }
            toOrder = if (index != ids.lastIndex) getQueueItemOrder(ids[index + 1]) else null

            deleteQueueItem(item)
            if (toOrder != null) {
                shiftUpOrders(fromOrder, toOrder!!)
            } else {
                shiftUpOrders(fromOrder)
            }
        }
    }

    @Transaction
    suspend fun reorderItemInQueue(id: Long, newOrder: Int) {
        val currentOrder = getQueueItemOrder(id)
        if (currentOrder == newOrder) return
        val swappingItemId = getQueueItem(currentOrder)
        updateQueueItem(id, newOrder)
        updateQueueItem(swappingItemId, currentOrder)
    }
}