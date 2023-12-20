package me.amirkazemzade.materialmusicplayer

import android.app.Application
import me.amirkazemzade.materialmusicplayer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MaterialMusicPlayerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MaterialMusicPlayerApplication)
            // Load modules
            modules(appModule)
        }
    }
}
