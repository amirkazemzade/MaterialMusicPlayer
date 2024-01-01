package me.amirkazemzade.materialmusicplayer.domain.model

import android.net.Uri

data class QueueItem(
    val id: Long = 0,
    val title: String,
    val artist: String,
    val uri: Uri,
)