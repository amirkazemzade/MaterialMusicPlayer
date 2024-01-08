package me.amirkazemzade.materialmusicplayer.data

import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.amirkazemzade.materialmusicplayer.data.mappers.mapToMediaItems
import me.amirkazemzade.materialmusicplayer.data.mappers.toMedaItemsWithStartPosition
import me.amirkazemzade.materialmusicplayer.data.mappers.toQueue
import me.amirkazemzade.materialmusicplayer.data.service.MusicQueueUpdateListener
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.QueueItem
import me.amirkazemzade.materialmusicplayer.domain.usecase.QueueUseCases
import org.koin.java.KoinJavaComponent.get


class MusicPlayerController(
    private val player: Player,
    private val scope: CoroutineScope,
    private val queueUseCases: QueueUseCases = get(QueueUseCases::class.java),
) : Player by player {

    private lateinit var musicQueueUpdateListener: MusicQueueUpdateListener

    init {
        addPlayerListener()
        initiatePlayerQueue()
    }

    private fun addPlayerListener() {
        musicQueueUpdateListener = MusicQueueUpdateListener(
            scope = scope,
            clearQueueUseCase = queueUseCases.clearQueueUseCase,
            updateQueueDataUseCase = queueUseCases.updateQueueDataUseCase,
        )

        player.addListener(musicQueueUpdateListener)
    }

    private fun initiatePlayerQueue() {
        if (player.mediaItemCount == 0) {
            scope.launch {
                setPlayerQueue()
            }
        }
    }

    @OptIn(UnstableApi::class)
    private suspend fun setPlayerQueue() {
        val queue = withContext(Dispatchers.IO) {
            queueUseCases.getQueueUseCase().toMedaItemsWithStartPosition()
        }
        if (queue.mediaItems.isEmpty()) return
        withContext(Dispatchers.Main) {
            player.apply {
                setMediaItems(queue.mediaItems, queue.startIndex, queue.startPositionMs)
                prepare()
            }
        }
    }

    fun play(musics: ImmutableList<MusicFile>, startIndex: Int = 0, startPositionMs: Long = 0) {
        scope.launch {
            val queue = musics.toQueue(startIndex, startPositionMs)
            queueUseCases.setQueueUseCase(queue)
        }
        val playlist = musics.mapToMediaItems()

        player.clearMediaItems()
        player.setMediaItems(playlist, startIndex, startPositionMs)
        player.prepare()
        player.play()
    }

    fun playNext(music: MusicFile) {
        scope.launch {
            queueUseCases.addItemToNextUseCase(music, player.currentMediaItemIndex)
        }
    }

    fun playNext(musics: List<MusicFile>) {
        scope.launch {
            queueUseCases.addItemsToNextUseCase(musics, player.currentMediaItemIndex)
        }
    }

    fun addToQueue(music: MusicFile) {
        scope.launch {
            queueUseCases.addItemToEndUseCase(music)
        }
    }

    fun addToQueue(musics: List<MusicFile>) {
        scope.launch {
            queueUseCases.addItemsToEndUseCase(musics)
        }
    }

    fun removeFromQueue(item: QueueItem) {
        scope.launch {
            queueUseCases.removeItemFromQueueUseCase(item)
        }
    }

    fun removeFromQueue(items: List<QueueItem>) {
        scope.launch {
            queueUseCases.removeItemsFromQueueUseCase(items)
        }
    }

    fun reorderItem(item: QueueItem, newOrder: Int) {
        scope.launch {
            queueUseCases.reorderItemInQueueUseCase(item.id, newOrder)
        }
    }

    fun onPlay() {
        Util.handlePlayButtonAction(player)
    }

    fun onPause() {
        Util.handlePauseButtonAction(player)
    }

    fun onNext() {
        if (player.isCommandAvailable(Player.COMMAND_SEEK_TO_NEXT)) player.seekToNext()
    }

    fun onPrevious() {
        if (player.isCommandAvailable(Player.COMMAND_SEEK_TO_PREVIOUS)) player.seekToPrevious()
    }

    fun onSeekTo(positionMs: Long) {
        if (player.isCommandAvailable(Player.COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM)) player.seekTo(
            positionMs
        )
    }

    fun onShuffleChange(shuffleEnable: Boolean) {
        if (player.isCommandAvailable(Player.COMMAND_SET_SHUFFLE_MODE)) player.shuffleModeEnabled =
            shuffleEnable
    }

    override fun release() {
        player.removeListener(musicQueueUpdateListener)
        player.release()
    }
}