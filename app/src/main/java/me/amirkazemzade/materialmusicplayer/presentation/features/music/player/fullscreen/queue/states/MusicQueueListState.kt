package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.states

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItemWithMusic

@Immutable
data class MusicQueueListState(
    val isLoading: Boolean = true,
    val items: ImmutableList<QueueItemWithMusic> = persistentListOf(),
)
