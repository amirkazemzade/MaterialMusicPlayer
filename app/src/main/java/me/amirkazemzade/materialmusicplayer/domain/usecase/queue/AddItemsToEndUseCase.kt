package me.amirkazemzade.materialmusicplayer.domain.usecase.queue

import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository

class AddItemsToEndUseCase(private val repository: QueueRepository) {

    suspend operator fun invoke(musics: List<MusicFile>) {
        repository.addItemsToEndOfQueue(
            musicIds = musics.map { music -> music.id },
        )
    }
}
