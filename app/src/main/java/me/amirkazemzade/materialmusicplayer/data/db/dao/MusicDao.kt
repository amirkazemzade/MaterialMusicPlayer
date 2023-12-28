package me.amirkazemzade.materialmusicplayer.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.data.db.entity.MusicFileEntity

@Dao
interface MusicDao {
    @Upsert
    suspend fun upsertMusics(music: List<MusicFileEntity>)

    @Delete
    suspend fun deleteMusic(music: MusicFileEntity)

    @Query("SELECT * FROM musicfileentity ORDER BY dateAdded ASC")
    fun getMusicsOrderedByDateAddedAscending(): Flow<List<MusicFileEntity>>

    @Query("SELECT * FROM musicfileentity ORDER BY dateAdded DESC")
    fun getMusicsOrderedByDateAddedDescending(): Flow<List<MusicFileEntity>>

    fun getMusicsOrderedByDateAdded(ascending: Boolean): Flow<List<MusicFileEntity>> =
        if (ascending) getMusicsOrderedByDateAddedAscending()
        else getMusicsOrderedByDateAddedDescending()

    @Query("SELECT * FROM musicfileentity ORDER BY title ASC")
    fun getMusicsOrderedByTitleAscending(): Flow<List<MusicFileEntity>>

    @Query("SELECT * FROM musicfileentity ORDER BY title DESC")
    fun getMusicsOrderedByTitleDescending(): Flow<List<MusicFileEntity>>

    fun getMusicsOrderedByTitle(ascending: Boolean): Flow<List<MusicFileEntity>> =
        if (ascending) getMusicsOrderedByTitleAscending()
        else getMusicsOrderedByTitleDescending()

    @Query("SELECT * FROM musicfileentity ORDER BY artist ASC")
    fun getMusicsOrderedByArtistAscending(): Flow<List<MusicFileEntity>>

    @Query("SELECT * FROM musicfileentity ORDER BY artist DESC")
    fun getMusicsOrderedByArtistDescending(): Flow<List<MusicFileEntity>>

    fun getMusicsOrderedByArtist(ascending: Boolean): Flow<List<MusicFileEntity>> =
        if (ascending) getMusicsOrderedByArtistAscending()
        else getMusicsOrderedByArtistDescending()

}