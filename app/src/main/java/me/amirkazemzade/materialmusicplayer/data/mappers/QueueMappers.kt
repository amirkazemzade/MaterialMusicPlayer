package me.amirkazemzade.materialmusicplayer.data.mappers

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import kotlinx.collections.immutable.toImmutableList
import me.amirkazemzade.materialmusicplayer.data.db.dto.QueueDataWithItemsDto
import me.amirkazemzade.materialmusicplayer.data.db.dto.QueueItemWithMusicDto
import me.amirkazemzade.materialmusicplayer.data.db.dto.QueueMetadataItemDto
import me.amirkazemzade.materialmusicplayer.data.db.dto.QueueWithMetadataItemsDto
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueDataEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueItemEntity
import me.amirkazemzade.materialmusicplayer.domain.model.Queue
import me.amirkazemzade.materialmusicplayer.domain.model.QueueData
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithData
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic

fun Queue.toQueueDataWithItems() = QueueDataWithItemsDto(
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

fun QueueWithMetadataItemsDto?.toQueue(): Queue {
    if (this == null) {
        return Queue()
    }
    return Queue(
        musics = items.mapToQueueItemsWithData(),
        currentIndex = queue.currentIndex,
        currentPositionMs = queue.currentPositionMs,
    )
}

fun Iterable<QueueMetadataItemDto>.mapToQueueItemsWithData() =
    map { item -> item.toQueueItemWithData() }.toImmutableList()

fun Iterable<QueueItemWithMusicDto>.mapToQueueItemsWithMusic() =
    map { item -> item.toQueueItemWithMusic() }.toImmutableList()

private fun QueueItemWithMusicDto.toQueueItemWithMusic() = QueueItemWithMusic(
    id = queueItem.id,
    order = queueItem.order,
    music = music.toMusicFile(),
)

fun QueueMetadataItemDto.toQueueItemWithData() =
    QueueItemWithData(
        id = id,
        musicId = musicId,
        order = order,
        title = title,
        artist = artist,
        uri = uri,
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
