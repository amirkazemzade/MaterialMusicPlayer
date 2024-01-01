package me.amirkazemzade.materialmusicplayer.data.mappers

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import kotlinx.collections.immutable.toImmutableList
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueItemEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueWithItems
import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItem

fun Queue.toQueueWithItems() = QueueWithItems(
    queue = QueueEntity(
        currentIndex = currentIndex,
        currentPositionMs = currentPositionMs,
    ),
    items = musics.map { music ->
        QueueItemEntity(
            title = music.title,
            artist = music.artist,
            uri = music.uri,
        )
    }
)

fun QueueWithItems?.toQueue(): Queue {
    if (this == null) {
        return Queue()
    }
    return Queue(
        musics = items.map { item ->
            QueueItem(
                id = item.id,
                title = item.title,
                artist = item.artist,
                uri = item.uri,
            )
        }.toImmutableList(),
        currentIndex = queue.currentIndex,
        currentPositionMs = queue.currentPositionMs,
    )
}

@OptIn(UnstableApi::class)
fun Queue.toMedaItemsWithStartPosition(): MediaSession.MediaItemsWithStartPosition {
    return MediaSession.MediaItemsWithStartPosition(
        musics.map { music ->
            MediaItem.Builder()
                .setMediaId(music.id.toString())
                .setUri(music.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(music.title)
                        .setArtist(music.artist)
                        .build()
                )
                .build()
        },
        currentIndex,
        currentPositionMs,
    )
}

fun Player.toQueue(unknownText: String): Queue {
    val currentPositionMs = contentPosition
    val currentIndex = currentMediaItemIndex
    val mediaItems = buildList {
        for (i in 0..mediaItemCount) {
            add(getMediaItemAt(i))
        }
    }
    val queue = Queue(
        musics = mediaItems.mapNotNull { mediaItem ->
            if (mediaItem.localConfiguration == null) {
                return@mapNotNull null
            }
            QueueItem(
                title = mediaItem.mediaMetadata.title?.toString() ?: unknownText,
                artist = mediaItem.mediaMetadata.artist?.toString() ?: unknownText,
                uri = mediaItem.localConfiguration!!.uri
            )
        }.toImmutableList(),
        currentIndex = currentIndex,
        currentPositionMs = currentPositionMs
    )
    return queue
}
