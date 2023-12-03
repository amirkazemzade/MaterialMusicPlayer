package me.amirkazemzade.materialmusicplayer.data.mappers

import android.content.ContentUris
import android.database.Cursor
import android.media.MediaMetadataRetriever
import android.os.Build
import android.provider.MediaStore
import androidx.core.database.getStringOrNull
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile

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
