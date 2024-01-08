package me.amirkazemzade.materialmusicplayer.data.db.entities.queue

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import me.amirkazemzade.materialmusicplayer.data.db.entities.music.MusicEntity

@Entity(
    indices = [
        Index(
            value = [QueueItemEntity.ChildColumns.MUSIC_ID],
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = MusicEntity::class,
            parentColumns = [MusicEntity.ParentColumns.ID],
            childColumns = [QueueItemEntity.ChildColumns.MUSIC_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class QueueItemEntity(
    val musicId: Long,
    val order: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
) {
    object ChildColumns {
        const val MUSIC_ID = "musicId"
    }
}