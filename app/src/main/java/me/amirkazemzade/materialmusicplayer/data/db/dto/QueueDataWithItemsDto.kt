package me.amirkazemzade.materialmusicplayer.data.db.dto

import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueDataEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueItemEntity

data class QueueDataWithItemsDto(
    val data: QueueDataEntity,
    val items: List<QueueItemEntity>,
)