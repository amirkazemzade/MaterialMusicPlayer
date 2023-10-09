package me.amirkazemzade.materialmusicplayer.di

import me.amirkazemzade.materialmusicplayer.data.repository.MusicRepository
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepositoryImpl
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase
import me.amirkazemzade.materialmusicplayer.presentation.features.musiclist.MusicListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single<MusicRepository> { MusicRepositoryImpl(get()) }

    // Use Cases
    singleOf(::GetMusicListUseCase)

    // ViewModels
    viewModelOf(::MusicListViewModel)
}
