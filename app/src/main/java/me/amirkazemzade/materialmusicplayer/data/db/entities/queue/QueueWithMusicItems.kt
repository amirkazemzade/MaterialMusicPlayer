package me.amirkazemzade.materialmusicplayer.data.db.entities.queue

data class QueueWithMusicItems(
    val queue: QueueDataEntity,
    val items: List<QueueMusicItem>,
)
