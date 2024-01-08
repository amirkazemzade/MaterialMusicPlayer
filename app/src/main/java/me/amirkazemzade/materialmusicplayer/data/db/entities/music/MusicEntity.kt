package me.amirkazemzade.materialmusicplayer.data.db.entities.music

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RenameTable

@RenameTable.Entries(
    RenameTable(
        fromTableName = "MusicFileEntity",
        toTableName = "MusicEntity",
    )
)
@Entity
data class MusicEntity(
    @PrimaryKey
    val id: Long,
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
) {
    object ParentColumns {
        const val ID = "id"
    }
}