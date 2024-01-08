package me.amirkazemzade.materialmusicplayer.data.db.entities.queue

import android.net.Uri

data class QueueMusicItem(
    val id: Long,
    val musicId: Long,
    val order: Int,
    val uri: Uri,
    val title: String,
    val artist: String,
)
