package me.amirkazemzade.materialmusicplayer.data.db.migrations

import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec

@RenameTable(
    fromTableName = "MusicFileEntity",
    toTableName = "MusicEntity"
)
class AutoMigrationFrom1To2 : AutoMigrationSpec