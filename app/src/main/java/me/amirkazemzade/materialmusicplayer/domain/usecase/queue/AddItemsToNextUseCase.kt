package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class AddItemsToNextUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(musics: List<MusicFile>, currentIndex: Int) {
        repository.addItemsToQueue(
            musicIds = musics.map { music -> music.id },
            startingOrder = currentIndex + 1,
        )
    }
}
