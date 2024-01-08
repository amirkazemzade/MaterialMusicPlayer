package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class AddItemToNextUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(music: MusicFile, currentIndex: Int) {
        repository.addItemToQueue(musicId = music.id, order = currentIndex + 1)
    }
}
