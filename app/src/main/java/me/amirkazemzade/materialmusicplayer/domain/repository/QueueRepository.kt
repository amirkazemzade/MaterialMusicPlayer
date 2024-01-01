package me.amirkazemzade.materialmusicplayer.domain.repository

import me.amirkazemzade.materialmusicplayer.domain.model.Queue

interface QueueRepository {

    suspend fun setQueue(queue: Queue)

    suspend fun getQueue(): Queue

    suspend fun clearQueue()
}