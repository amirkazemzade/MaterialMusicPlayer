package me.amirkazemzade.materialmusicplayer.data.mappers

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.core.database.getStringOrNull
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import kotlinx.collections.immutable.toImmutableList
import me.amirkazemzade.materialmusicplayer.data.db.entities.music.MusicEntity
import me.amirkazemzade.materialmusicplayer.data.extensions.toBitmap
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithData
import java.io.IOException


// Cursor Mappers

fun Cursor.toMusicFile(context: Context): MusicFile {
    val id = getLong(getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
    val filePath = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
    val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
    val artworkThumbnail = thumbnailExtractor(context, uri, filePath)
    val title = getStringOrNull(MediaStore.Audio.Media.TITLE)
    val artist = getStringOrNull(MediaStore.Audio.Media.ARTIST)
    val album = getStringOrNull(MediaStore.Audio.Media.ALBUM)
    val dateAdded = getStringOrNull(MediaStore.Audio.Media.DATE_ADDED)
    val dateModified = getStringOrNull(MediaStore.Audio.Media.DATE_MODIFIED)
    val duration = getStringOrNull(MediaStore.Audio.Media.DURATION)
    val genre =
        if (Build.VERSION.SDK_INT >= 30) getStringOrNull(MediaStore.Audio.Media.GENRE)
        else null
    val year = getStringOrNull(MediaStore.Audio.Media.YEAR)

    return MusicFile(
        id = id,
        title = title,
        artist = artist,
        album = album,
        filePath = filePath,
        dateAdded = dateAdded,
        dateModified = dateModified,
        duration = duration,
        genre = genre,
        year = year,
        artwork = artworkThumbnail,
        uri = uri,
    )
}

private fun Cursor.getStringOrNull(columnName: String): String? =
    getStringOrNull(getColumnIndexOrThrow(columnName))

private fun thumbnailExtractor(
    context: Context,
    uri: Uri,
    filePath: String?,
): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver.loadThumbnail(uri, Size(192, 192), null)
        } else {
            val mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(filePath)
            val albumCoverImage = mediaMetadataRetriever.embeddedPicture
            mediaMetadataRetriever.release()
            ThumbnailUtils.extractThumbnail(albumCoverImage?.toBitmap(), 192, 192)
        }
    } catch (e: IOException) {
        null
    }
}

// MusicEntity Mappers

fun MusicEntity.toMusicFile() = MusicFile(
    id = id,
    title = title,
    artist = artist,
    album = album,
    filePath = filePath,
    dateAdded = dateAdded,
    dateModified = dateModified,
    duration = duration,
    genre = genre,
    year = year,
    artwork = artworkThumbnail,
    uri = uri,
)

fun MusicFile.toMusicEntity() = MusicEntity(
    id = id,
    title = title,
    artist = artist,
    album = album,
    filePath = filePath,
    dateAdded = dateAdded,
    dateModified = dateModified,
    duration = duration,
    genre = genre,
    year = year,
    artworkThumbnail = artwork,
    uri = uri,
)

// MediaItem Mappers

fun MusicFile.toMediaItem() =
    MediaItem.Builder()
        .setMediaId(id.toString())
        .setUri(uri)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setTitle(title)
                .setArtist(artist)
                .build()
        )
        .build()

fun Iterable<MusicFile>.mapToMediaItems(): List<MediaItem> =
    map { musicFile -> musicFile.toMediaItem() }

// QueueItem Mappers

fun Iterable<MusicFile>.mapToQueueItemsWithData(): List<QueueItemWithData> =
    mapIndexed { index, music ->
        QueueItemWithData(
            musicId = music.id,
            order = index,
            title = music.title,
            artist = music.artist,
            uri = music.uri,
        )
    }

fun Iterable<MusicFile>.toQueue(startIndex: Int, startPositionMs: Long): Queue =
    Queue(
        musics = mapToQueueItemsWithData().toImmutableList(),
        currentIndex = startIndex,
        currentPositionMs = startPositionMs,
    )