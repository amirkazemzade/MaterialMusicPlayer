package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class AddItemToEndUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(music: MusicFile) {
        repository.addItemToEndOfQueue(musicId = music.id)
    }
}
