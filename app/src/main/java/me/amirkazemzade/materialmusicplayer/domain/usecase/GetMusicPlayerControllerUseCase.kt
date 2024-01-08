package me.amirkazemzade.materialmusicplayer.domain.usecase

import android.app.Application
import android.content.ComponentName
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.amirkazemzade.materialmusicplayer.data.MusicPlayerController
import me.amirkazemzade.materialmusicplayer.data.service.MusicSessionService
import me.amirkazemzade.materialmusicplayer.domain.model.Status

class GetMusicPlayerControllerUseCase(private val app: Application) {

    operator fun invoke(scope: CoroutineScope): StateFlow<Status<MusicPlayerController>> {
        val musicPlayerControllerFlow =
            MutableStateFlow<Status<MusicPlayerController>>(Status.Loading())

        val sessionToken =
            SessionToken(
                app,
                ComponentName(app, MusicSessionService::class.java),
            )
        val controllerFuture = MediaController.Builder(app, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                val mediaController = controllerFuture.get()
                val musicPlayerController = MusicPlayerController(
                    player = mediaController,
                    scope = scope
                )
                musicPlayerControllerFlow.value = Status.Success(musicPlayerController)
            },
            MoreExecutors.directExecutor(),
        )

        return musicPlayerControllerFlow
    }
}
