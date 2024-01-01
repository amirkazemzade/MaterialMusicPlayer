package me.amirkazemzade.materialmusicplayer.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import me.amirkazemzade.materialmusicplayer.data.db.entities.version.VersionEntity

@Dao
interface VersionDao {
    @Upsert
    suspend fun upsertVersion(version: VersionEntity)

    @Query("DELETE FROM versionentity")
    suspend fun deleteVersion()

    @Transaction
    suspend fun setVersion(version: VersionEntity) {
        deleteVersion()
        upsertVersion(version)
    }

    @Query("SELECT * FROM versionentity LIMIT 1")
    suspend fun getVersion(): VersionEntity?
}