package me.amirkazemzade.materialmusicplayer.data.db.entities.queue

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = QueueEntity::class,
            parentColumns = [QueueEntity.ParentColumns.ID],
            childColumns = [QueueItemEntity.ChildColumns.QUEUE_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        )
    ]
)
data class QueueItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val queueId: Long = 0,
    val title: String,
    val artist: String,
    val uri: Uri,
) {
    object ChildColumns {
        const val QUEUE_ID = "queueId"
    }
}