package me.amirkazemzade.materialmusicplayer.data.db.dto

import androidx.room.Embedded
import androidx.room.Relation
import me.amirkazemzade.materialmusicplayer.data.db.entities.music.MusicEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueItemEntity

data class QueueItemWithMusicDto(
    @Embedded
    val queueItem: QueueItemEntity,
    @Relation(
        entityColumn = MusicEntity.ParentColumns.ID,
        parentColumn = QueueItemEntity.ChildColumns.MUSIC_ID,
    )
    val music: MusicEntity,
)