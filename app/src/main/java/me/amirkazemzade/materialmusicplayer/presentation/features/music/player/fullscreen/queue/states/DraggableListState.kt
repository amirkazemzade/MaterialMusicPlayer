package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.states

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic

data class DraggableListState(
    val items: ImmutableList<QueueItemWithMusic> = persistentListOf(),
    val draggingItemIndex: Int? = null,
    val currentPositionIndex: Int? = null,
)