package me.amirkazemzade.materialmusicplayer.di

import androidx.room.Room
import me.amirkazemzade.materialmusicplayer.data.db.MusicDatabase
import me.amirkazemzade.materialmusicplayer.data.repository.MusicRepositoryImpl
import me.amirkazemzade.materialmusicplayer.data.source.music.CacheMusicSourceImp
import me.amirkazemzade.materialmusicplayer.data.source.music.MediaStoreMusicSource
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository
import me.amirkazemzade.materialmusicplayer.domain.source.CacheMusicSource
import me.amirkazemzade.materialmusicplayer.domain.source.RemoteMusicSource
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

        // Database
        single { Room.databaseBuilder(get(), MusicDatabase::class.java, "musics.db").build() }
        single { get<MusicDatabase>().versionDao }
        single { get<MusicDatabase>().musicDao }

        // DataSources
        single<RemoteMusicSource> { MediaStoreMusicSource(get()) }
        single<CacheMusicSource> { CacheMusicSourceImp(get(), get()) }

        // Repositories
        single<MusicRepository> { MusicRepositoryImpl(get(), get()) }

        // Use Cases
        singleOf(::GetMusicListUseCase)
        singleOf(::GetMediaControllerUseCase)

        // ViewModels
        viewModelOf(::MusicControllerViewModel)
        viewModelOf(::MusicListViewModel)
    }
