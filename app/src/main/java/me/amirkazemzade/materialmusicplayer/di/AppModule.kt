package me.amirkazemzade.materialmusicplayer.di

import androidx.room.Room
import me.amirkazemzade.materialmusicplayer.data.db.MusicDatabase
import me.amirkazemzade.materialmusicplayer.data.repository.MusicRepositoryImpl
import me.amirkazemzade.materialmusicplayer.data.repository.QueueRepositoryImpl
import me.amirkazemzade.materialmusicplayer.data.source.music.CacheMusicSourceImp
import me.amirkazemzade.materialmusicplayer.data.source.music.MediaStoreMusicSource
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository
import me.amirkazemzade.materialmusicplayer.domain.repository.QueueRepository
import me.amirkazemzade.materialmusicplayer.domain.source.CacheMusicSource
import me.amirkazemzade.materialmusicplayer.domain.source.RemoteMusicSource
import me.amirkazemzade.materialmusicplayer.domain.usecase.ClearQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMediaControllerUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetMusicListUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.GetQueueUseCase
import me.amirkazemzade.materialmusicplayer.domain.usecase.SetQueueUseCase
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
        single {
            Room
                .databaseBuilder(get(), MusicDatabase::class.java, "musics.db")
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<MusicDatabase>().versionDao }
        single { get<MusicDatabase>().musicDao }
        single { get<MusicDatabase>().queueDao }

        // DataSources
        single<RemoteMusicSource> { MediaStoreMusicSource(get()) }
        single<CacheMusicSource> { CacheMusicSourceImp(get(), get()) }

        // Repositories
        single<MusicRepository> { MusicRepositoryImpl(get(), get()) }
        single<QueueRepository> { QueueRepositoryImpl(get()) }

        // Use Cases
        singleOf(::GetMusicListUseCase)
        singleOf(::GetMediaControllerUseCase)
        singleOf(::GetQueueUseCase)
        singleOf(::SetQueueUseCase)
        singleOf(::ClearQueueUseCase)

        // ViewModels
        viewModelOf(::MusicControllerViewModel)
        viewModelOf(::MusicListViewModel)
    }
