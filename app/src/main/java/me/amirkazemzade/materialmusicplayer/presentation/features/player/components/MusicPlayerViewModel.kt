package me.amirkazemzade.materialmusicplayer.presentation.features.player.components

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player

class MusicPlayerViewModel(
    val player: Player,
//    musicFile: MusicFile
    musicUri: Uri
) : ViewModel() {

    init {
        player.prepare()
        playMusic(musicUri)
    }

    fun playMusic(uri: Uri) {
        player.setMediaItem(
            MediaItem.fromUri(uri)
        )
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}
