package me.amirkazemzade.materialmusicplayer.data.db.dto

import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueDataEntity

data class QueueWithMetadataItemsDto(
    val queue: QueueDataEntity,
    val items: List<QueueMetadataItemDto>,
)
