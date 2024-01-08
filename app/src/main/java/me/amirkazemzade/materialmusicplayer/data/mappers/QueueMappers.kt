package me.amirkazemzade.materialmusicplayer.data.mappers

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import kotlinx.collections.immutable.toImmutableList
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueDataEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueDataWithItems
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueItemEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueMusicItem
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueWithMusicItems
import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.model.QueueData
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItem
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithData

fun Queue.toQueueDataWithItems() = QueueDataWithItems(
    data = QueueDataEntity(
        currentIndex = currentIndex,
        currentPositionMs = currentPositionMs,
    ),
    items = musics.map { music ->
        QueueItemEntity(
            id = music.id,
            musicId = music.musicId,
            order = music.order,
        )
    }
)

fun QueueData.toQueueEntity() = QueueDataEntity(
    currentIndex = currentIndex,
    currentPositionMs = currentPositionMs,
)

fun QueueWithMusicItems?.toQueue(): Queue {
    if (this == null) {
        return Queue()
    }
    return Queue(
        musics = items.mapToQueueItemsWithData(),
        currentIndex = queue.currentIndex,
        currentPositionMs = queue.currentPositionMs,
    )
}

fun Iterable<QueueMusicItem>.mapToQueueItemsWithData() =
    map { item -> item.toQueueItemWithData() }.toImmutableList()

fun QueueMusicItem.toQueueItemWithData() =
    QueueItemWithData(
        id = id,
        musicId = musicId,
        order = order,
        title = title,
        artist = artist,
        uri = uri,
    )

fun QueueItem.toQueueItemEntity() = QueueItemEntity(
    musicId = musicId,
    order = order,
    id = id
)

@OptIn(UnstableApi::class)
fun Queue.toMedaItemsWithStartPosition(): MediaSession.MediaItemsWithStartPosition {
    return MediaSession.MediaItemsWithStartPosition(
        musics.map { music ->
            MediaItem.Builder()
                .setMediaId(music.musicId.toString())
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
