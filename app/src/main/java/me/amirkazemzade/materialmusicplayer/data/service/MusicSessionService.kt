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
import me.amirkazemzade.materialmusicplayer.data.MusicPlayerController
import me.amirkazemzade.materialmusicplayer.domain.usecase.queue.GetQueueUseCase
import me.amirkazemzade.materialmusicplayer.presentation.MainActivity
import org.koin.java.KoinJavaComponent.get

class MusicSessionService(
    getQueueUseCase: GetQueueUseCase = get(GetQueueUseCase::class.java),
) : MediaLibraryService() {

    private var mediaLibrarySession: MediaLibrarySession? = null

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val callback = MusicSessionCallback(scope = scope, getQueueUseCase = getQueueUseCase)


    private var mediaController: MediaController? = null

    override fun onGetSession(controllerInfo: ControllerInfo) = mediaLibrarySession

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer
            .Builder(this)
            .build()
        val musicPlayer = MusicPlayerController(
            player = player,
            scope = scope,
        )

        val mainActivityIntent = Intent(this, MainActivity::class.java)
        val sessionActivity = PendingIntent.getActivity(
            this,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        mediaLibrarySession =
            MediaLibrarySession
                .Builder(this, musicPlayer, callback)
                .setSessionActivity(sessionActivity)
                .build()

        createMediaController()
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
            player.release()
            release()
            mediaLibrarySession = null
        }
        super.onDestroy()
    }
}