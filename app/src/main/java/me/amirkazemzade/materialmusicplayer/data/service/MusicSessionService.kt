package me.amirkazemzade.materialmusicplayer.data.service

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession.ControllerInfo
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.data.mappers.toMedaItemsWithStartPosition
import me.amirkazemzade.materialmusicplayer.domain.usecase.ClearQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.SetQueueUseCase
import me.amirkazemzade.materialmusicplayer.presentation.MainActivity
import org.koin.java.KoinJavaComponent.get

class MusicSessionService(
    private val getQueueUseCase: GetQueueUseCase = get(GetQueueUseCase::class.java),
    private val setQueueUseCase: SetQueueUseCase = get(SetQueueUseCase::class.java),
    private val clearQueueUseCase: ClearQueueUseCase = get(ClearQueueUseCase::class.java),
) : MediaLibraryService() {

    private var mediaLibrarySession: MediaLibrarySession? = null

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val callback = MusicSessionCallback(scope = scope, getQueueUseCase = getQueueUseCase)

    private lateinit var musicQueueUpdateListener: MusicQueueUpdateListener

    private var mediaController: MediaController? = null

    override fun onGetSession(controllerInfo: ControllerInfo) = mediaLibrarySession

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer
            .Builder(this)
            .build()
            .apply {
                musicQueueUpdateListener = MusicQueueUpdateListener(
                    scope = scope,
                    setQueueUseCase = setQueueUseCase,
                    clearQueueUseCase = clearQueueUseCase,
                    unknownText = resources.getString(R.string.unknown),
                )
                addListener(musicQueueUpdateListener)
            }

        val mainActivityIntent = Intent(this, MainActivity::class.java)
        val sessionActivity = PendingIntent.getActivity(
            this,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        mediaLibrarySession =
            MediaLibrarySession
                .Builder(this, player, callback)
                .setSessionActivity(sessionActivity)
                .build()

        createMediaController()

        scope.launch {
            val queue = getQueueUseCase().toMedaItemsWithStartPosition()
            withContext(Dispatchers.Main) {
                mediaLibrarySession?.player?.apply {
                    setMediaItems(queue.mediaItems, queue.startIndex, queue.startPositionMs)
                    prepare()
                }
            }
        }
    }

    private fun createMediaController() {
        val sessionToken =
            SessionToken(this, ComponentName(this, MusicSessionService::class.java))
        val controllerFuture =
            MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture.addListener(
            { mediaController = controllerFuture.get() },
            MoreExecutors.directExecutor(),
        )
    }

    override fun onDestroy() {
        mediaLibrarySession?.run {
            mediaController?.release()
            player.removeListener(musicQueueUpdateListener)
            player.release()
            release()
            mediaLibrarySession = null
        }
        super.onDestroy()
    }
}