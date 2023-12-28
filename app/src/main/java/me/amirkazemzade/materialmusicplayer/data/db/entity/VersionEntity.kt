package me.amirkazemzade.materialmusicplayer.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VersionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val version: String,
    val generation: Long? = null,
)
