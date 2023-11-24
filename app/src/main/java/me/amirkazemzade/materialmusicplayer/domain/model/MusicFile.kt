package me.amirkazemzade.materialmusicplayer.domain.model

import android.content.ContentUris
import android.database.Cursor
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.database.getStringOrNull

data class MusicFile(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val filePath: String,
    val dateAdded: String,
    val dateModified: String,
    val duration: String,
    val genre: String? = null,
    val year: String? = null,
    val albumCover: ByteArray? = null,
    val uri: Uri
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MusicFile

        return id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + artist.hashCode()
        result = 31 * result + album.hashCode()
        result = 31 * result + filePath.hashCode()
        result = 31 * result + dateAdded.hashCode()
        result = 31 * result + dateModified.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + (genre?.hashCode() ?: 0)
        result = 31 * result + (year?.hashCode() ?: 0)
        result = 31 * result + (albumCover?.contentHashCode() ?: 0)
        return result
    }
}

fun Cursor.toMusicFile(): MusicFile {
    val id = getLong(getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
    val filePath = getString(
        getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
    )
    val mediaMetadataRetriever = MediaMetadataRetriever()
    mediaMetadataRetriever.setDataSource(filePath)
    val albumCoverImage = mediaMetadataRetriever.embeddedPicture
    mediaMetadataRetriever.release()

    return MusicFile(
        id = id,
        title = getString(
            getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        ),
        artist = getString(
            getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        ),
        album = getString(
            getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
        ),
        filePath = filePath,
        dateAdded = getString(
            getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
        ),
        dateModified = getString(
            getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)
        ),
        duration = getString(
            getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        ),
        genre = if (Build.VERSION.SDK_INT >= 30) {
            getStringOrNull(getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE))
        } else {
            null
        },
        year = getStringOrNull(
            getColumnIndex(MediaStore.Audio.Media.YEAR)
        ),
        albumCover = albumCoverImage,
        uri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            id
        )
    )
}

val musicProjection = arrayOf(
    MediaStore.Audio.Media._ID,
    MediaStore.Audio.Media.TITLE,
    MediaStore.Audio.Media.ARTIST,
    MediaStore.Audio.Media.ALBUM,
    MediaStore.Audio.Media.DATA,
    MediaStore.Audio.Media.DATE_ADDED,
    MediaStore.Audio.Media.DATE_MODIFIED,
    MediaStore.Audio.Media.DURATION,
    MediaStore.Audio.Media.GENRE,
    MediaStore.Audio.Media.YEAR
)
