package me.amirkazemzade.materialmusicplayer.di

import me.amirkazemzade.materialmusicplayer.data.repository.MusicRepositoryImpl
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMediaControllerUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase
import me.amirkazemzade.materialmusicplayer.presentation.features.music.MusicControllerViewModel
import me.amirkazemzade.materialmusicplayer.presentation.features.music.list.MusicListViewModel
import me.amirkazemzade.materialmusicplayer.presentation.navigation.Navigator
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule =
    module {
        // Navigation
        singleOf(::Navigator)

        // Repositories
        single<MusicRepository> { MusicRepositoryImpl(get()) }

        // Use Cases
        singleOf(::GetMusicListUseCase)
        singleOf(::GetMediaControllerUseCase)

        // ViewModels
        viewModelOf(::MusicControllerViewModel)
        viewModelOf(::MusicListViewModel)
    }
