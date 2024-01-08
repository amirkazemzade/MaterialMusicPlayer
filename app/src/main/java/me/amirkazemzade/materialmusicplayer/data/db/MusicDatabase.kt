package me.amirkazemzade.materialmusicplayer.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.amirkazemzade.materialmusicplayer.data.db.dao.MusicDao
import me.amirkazemzade.materialmusicplayer.data.db.dao.QueueDao
import me.amirkazemzade.materialmusicplayer.data.db.dao.VersionDao
import me.amirkazemzade.materialmusicplayer.data.db.entities.Converters
import me.amirkazemzade.materialmusicplayer.data.db.entities.music.MusicEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueDataEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueItemEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.version.VersionEntity
import me.amirkazemzade.materialmusicplayer.data.db.migrations.AutoMigrationFrom1To2

@TypeConverters(Converters::class)
@Database(
    entities = [VersionEntity::class, MusicEntity::class, QueueDataEntity::class, QueueItemEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = AutoMigrationFrom1To2::class),
    ]
)

abstract class MusicDatabase : RoomDatabase() {
    abstract val versionDao: VersionDao
    abstract val musicDao: MusicDao
    abstract val queueDao: QueueDao
}