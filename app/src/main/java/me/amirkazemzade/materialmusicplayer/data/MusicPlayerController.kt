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
import me.amirkazemzade.materialmusicplayer.domain.model.RepeatMode
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
        val isCommandsAvailable = withContext(Dispatchers.Main) {
            player.isCommandAvailable(Player.COMMAND_CHANGE_MEDIA_ITEMS) &&
                    player.isCommandAvailable(Player.COMMAND_PREPARE)
        }
        if (!isCommandsAvailable) return

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
        if (
            !player.isCommandAvailable(Player.COMMAND_CHANGE_MEDIA_ITEMS) ||
            !player.isCommandAvailable(Player.COMMAND_PREPARE)
        ) return

        scope.launch {
            val queue = musics.toQueue(startIndex, startPositionMs)
            queueUseCases.setQueueUseCase(queue)
        }
        val playlist = musics.mapToMediaItems()

        player.clearMediaItems()
        player.setMediaItems(playlist, startIndex, startPositionMs)
        player.prepare()
        onPlay()
    }

    fun play(index: Int) {
        if (!player.isCommandAvailable(Player.COMMAND_SEEK_TO_MEDIA_ITEM)) return
        player.seekTo(index, 0)
        onPlay()
    }

    fun playNext(music: MusicFile) {
        scope.launch {
            queueUseCases.addItemToNextUseCase(music, player.currentMediaItemIndex)
        }
    }

    fun playNext(musics: List<MusicFile>) {
        if (musics.isEmpty()) return
        if (musics.size == 1) playNext(musics.first())

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
        if (musics.isEmpty()) return
        if (musics.size == 1) addToQueue(musics.first())

        scope.launch {
            queueUseCases.addItemsToEndUseCase(musics)
        }
    }

    private fun removeFromQueue(id: Long) {
        scope.launch {
            queueUseCases.removeItemFromQueueUseCase(id)
        }
    }

    fun removeFromQueue(ids: List<Long>) {
        if (ids.isEmpty()) return
        if (ids.size == 1) {
            removeFromQueue(ids.first())
            return
        }

        scope.launch {
            queueUseCases.removeItemsFromQueueUseCase(ids)
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
        if (!player.isCommandAvailable(Player.COMMAND_SEEK_TO_NEXT)) return
        player.seekToNext()
    }

    fun onPrevious() {
        if (!player.isCommandAvailable(Player.COMMAND_SEEK_TO_PREVIOUS)) return
        player.seekToPrevious()
    }

    fun onSeekTo(positionMs: Long) {
        if (!player.isCommandAvailable(Player.COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM)) return
        player.seekTo(positionMs)
    }

    fun onShuffleChange(shuffleEnable: Boolean) {
        if (!player.isCommandAvailable(Player.COMMAND_SET_SHUFFLE_MODE)) return
        player.shuffleModeEnabled = shuffleEnable
    }

    fun onRepeatModeChange(repeatMode: RepeatMode) {
        if (!player.isCommandAvailable(Player.COMMAND_SET_REPEAT_MODE)) return
        player.repeatMode = repeatMode.numericValue
    }

    override fun release() {
        player.removeListener(musicQueueUpdateListener)
        player.release()
    }
}