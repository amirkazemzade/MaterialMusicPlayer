package me.amirkazemzade.materialmusicplayer.domain.model

import android.net.Uri

data class QueueItem(
    val musicId: Long,
    val order: Int,
    val id: Long = 0,
)

data class QueueItemWithData(
    val musicId: Long,
    val order: Int,
    val uri: Uri,
    val title: String?,
    val artist: String?,
    val id: Long = 0,
)

data class QueueItemWithMusic(
    val music: MusicFile,
    val order: Int,
    val id: Long = 0,
)