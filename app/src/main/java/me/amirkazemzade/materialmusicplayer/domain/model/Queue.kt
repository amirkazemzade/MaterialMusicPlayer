package me.amirkazemzade.materialmusicplayer.domain.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class Queue(
    val musics: ImmutableList<QueueItemWithData> = persistentListOf(),
    override val currentIndex: Int = 0,
    override val currentPositionMs: Long = 0,
) : QueueData

interface QueueData {
    val currentIndex: Int
    val currentPositionMs: Long

    companion object {
        fun build(currentIndex: Int = 0, currentPositionMs: Long = 0): QueueData =
            QueueDataImpl(currentIndex, currentPositionMs)

        internal data class QueueDataImpl(
            override val currentIndex: Int = 0,
            override val currentPositionMs: Long = 0,
        ) : QueueData
    }
}
