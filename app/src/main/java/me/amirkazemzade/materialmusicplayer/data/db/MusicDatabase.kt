package me.amirkazemzade.materialmusicplayer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.amirkazemzade.materialmusicplayer.data.db.dao.MusicDao
import me.amirkazemzade.materialmusicplayer.data.db.dao.VersionDao
import me.amirkazemzade.materialmusicplayer.data.db.entity.Converters
import me.amirkazemzade.materialmusicplayer.data.db.entity.MusicFileEntity
import me.amirkazemzade.materialmusicplayer.data.db.entity.VersionEntity

@TypeConverters(Converters::class)
@Database(
    entities = [VersionEntity::class, MusicFileEntity::class],
    version = 1,
    autoMigrations = []
)

abstract class MusicDatabase : RoomDatabase() {
    abstract val versionDao: VersionDao
    abstract val musicDao: MusicDao
}