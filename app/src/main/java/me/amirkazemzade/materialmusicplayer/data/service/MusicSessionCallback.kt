package me.amirkazemzade.materialmusicplayer.data.service

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.amirkazemzade.materialmusicplayer.data.mappers.toMedaItemsWithStartPosition
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetQueueUseCase

class MusicSessionCallback(
    private val scope: CoroutineScope,
    private val getQueueUseCase: GetQueueUseCase,
) :
    MediaLibraryService.MediaLibrarySession.Callback {

    @OptIn(UnstableApi::class)
    override fun onPlaybackResumption(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
    ): ListenableFuture<MediaSession.MediaItemsWithStartPosition> {
        val settable = SettableFuture.create<MediaSession.MediaItemsWithStartPosition>()
        scope.launch {
            val resumptionQueue = getQueueUseCase().toMedaItemsWithStartPosition()
            settable.set(resumptionQueue)
        }
        return settable
    }
}