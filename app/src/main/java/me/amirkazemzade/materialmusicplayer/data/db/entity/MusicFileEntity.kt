package me.amirkazemzade.materialmusicplayer.data.db.entity

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MusicFileEntity(
    val title: String?,
    val artist: String?,
    val album: String?,
    val filePath: String,
    val dateAdded: String?,
    val dateModified: String?,
    val duration: String?,
    val genre: String? = null,
    val year: String? = null,
    val artworkThumbnail: Bitmap? = null,
    val uri: Uri,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
)