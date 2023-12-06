package me.amirkazemzade.materialmusicplayer.domain.usecase

import android.app.Application
import android.content.ComponentName
import android.util.Log
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.amirkazemzade.materialmusicplayer.data.service.MusicSessionService
import me.amirkazemzade.materialmusicplayer.domain.model.Status

class GetMediaControllerUseCase(private val app: Application) {
    operator fun invoke(): StateFlow<Status<MediaController>> {
        val mediaController = MutableStateFlow<Status<MediaController>>(Status.Loading())

        val sessionToken =
            SessionToken(
                app,
                ComponentName(app, MusicSessionService::class.java),
            )
        val controllerFuture = MediaController.Builder(app, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                Log.d("miniplayer", "invoke: ${controllerFuture.get()}")
                mediaController.value = Status.Success(controllerFuture.get())
            },
            MoreExecutors.directExecutor(),
        )
        return mediaController
    }
}
