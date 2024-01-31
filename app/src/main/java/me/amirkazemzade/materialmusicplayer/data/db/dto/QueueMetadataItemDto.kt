package me.amirkazemzade.materialmusicplayer.data.db.dto

import android.net.Uri

data class QueueMetadataItemDto(
    val id: Long,
    val musicId: Long,
    val order: Int,
    val uri: Uri,
    val title: String,
    val artist: String,
)