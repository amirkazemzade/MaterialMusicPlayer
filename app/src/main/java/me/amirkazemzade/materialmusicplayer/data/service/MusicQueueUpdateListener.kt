package me.amirkazemzade.materialmusicplayer.data.service

import androidx.media3.common.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.amirkazemzade.materialmusicplayer.domain.model.QueueData
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.ClearQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.UpdateQueueDataUseCase

class MusicQueueUpdateListener(
    private val scope: CoroutineScope,
    private val clearQueueUseCase: ClearQueueUseCase,
    private val updateQueueDataUseCase: UpdateQueueDataUseCase,
) : Player.Listener {
    override fun onEvents(player: Player, events: Player.Events) {
        if (player.mediaItemCount == 0) {
            scope.launch {
                clearQueueUseCase()
            }
        } else {
            val data = QueueData.build(
                currentIndex = player.currentMediaItemIndex,
                currentPositionMs = player.currentPosition,
            )
            scope.launch {
                updateQueueDataUseCase(data)
            }
        }
        super.onEvents(player, events)
    }

}