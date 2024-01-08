package me.amirkazemzade.materialmusicplayer.data.db.entities.queue

data class QueueDataWithItems(
    val data: QueueDataEntity,
    val items: List<QueueItemEntity>,
)