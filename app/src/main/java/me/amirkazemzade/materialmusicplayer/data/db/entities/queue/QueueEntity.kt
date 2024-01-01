package me.amirkazemzade.materialmusicplayer.data.db.entities.queue

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QueueEntity(
    @PrimaryKey
    val id: Long = 0,
    val currentIndex: Int,
    val currentPositionMs: Long = 0,
) {
    object ParentColumns {
        const val ID = "id"
    }
}


