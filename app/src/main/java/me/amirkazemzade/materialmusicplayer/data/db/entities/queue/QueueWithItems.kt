package me.amirkazemzade.materialmusicplayer.data.db.entities.queue

import androidx.room.Embedded
import androidx.room.Relation

data class QueueWithItems(
    @Embedded
    val queue: QueueEntity,
    @Relation(
        parentColumn = QueueEntity.ParentColumns.ID,
        entityColumn = QueueItemEntity.ChildColumns.QUEUE_ID,
    )
    val items: List<QueueItemEntity>,
)