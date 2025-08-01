package me.amirkazemzade.materialmusicplayer

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import me.amirkazemzade.materialmusicplayer.data.db.MusicDatabase

fun buildMusicDatabase(): MusicDatabase = Room.inMemoryDatabaseBuilder(
    ApplicationProvider.getApplicationContext(),
    MusicDatabase::class.java,
)
    .allowMainThreadQueries()
    .build()