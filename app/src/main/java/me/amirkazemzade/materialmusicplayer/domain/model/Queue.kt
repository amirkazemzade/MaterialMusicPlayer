package me.amirkazemzade.materialmusicplayer.domain.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class Queue(
    val musics: ImmutableList<QueueItem> = persistentListOf(),
    val currentIndex: Int = 0,
    val currentPositionMs: Long = 0,
)
