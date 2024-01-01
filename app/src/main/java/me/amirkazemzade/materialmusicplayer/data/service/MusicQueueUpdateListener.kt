package me.amirkazemzade.materialmusicplayer.data.service

import androidx.media3.common.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.amirkazemzade.materialmusicplayer.data.mappers.toQueue
import me.amirkazemzade.materialmusicplayer.domain.usecase.ClearQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.SetQueueUseCase

class MusicQueueUpdateListener(
    private val scope: CoroutineScope,
    private val setQueueUseCase: SetQueueUseCase,
    private val clearQueueUseCase: ClearQueueUseCase,
    private val unknownText: String,
) : Player.Listener {
    override fun onEvents(player: Player, events: Player.Events) {
        if (player.mediaItemCount == 0) {
            scope.launch {
                clearQueueUseCase()
            }
        } else {
            val queue = player.toQueue(unknownText = unknownText)
            scope.launch {
                setQueueUseCase(queue)
            }
        }
        super.onEvents(player, events)
    }

}