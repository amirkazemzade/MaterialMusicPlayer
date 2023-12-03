package me.amirkazemzade.materialmusicplayer.di

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import me.amirkazemzade.materialmusicplayer.data.repository.MusicRepositoryImpl
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.MusicListViewModel
import me.amirkazemzade.materialmusicplayer.presentation.navigation.Navigator
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // Navigation
    singleOf(::Navigator)

    // Player
    single<Player> {
        ExoPlayer
            .Builder(get())
            .build()
    }

    // Repositories
    single<MusicRepository> { MusicRepositoryImpl(get()) }

    // Use Cases
    singleOf(::GetMusicListUseCase)

    // ViewModels
    viewModelOf(::MusicListViewModel)
}
