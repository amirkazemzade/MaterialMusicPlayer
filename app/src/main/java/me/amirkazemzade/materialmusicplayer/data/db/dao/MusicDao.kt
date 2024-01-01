package me.amirkazemzade.materialmusicplayer.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.amirkazemzade.materialmusicplayer.data.db.entities.music.MusicEntity

@Dao
interface MusicDao {
    @Upsert
    suspend fun upsertMusics(musics: List<MusicEntity>)

    @Delete
    suspend fun deleteMusic(music: MusicEntity)

    @Query("SELECT * FROM musicentity ORDER BY dateAdded ASC")
    fun getMusicsOrderedByDateAddedAscending(): Flow<List<MusicEntity>>

    @Query("SELECT * FROM musicentity ORDER BY dateAdded DESC")
    fun getMusicsOrderedByDateAddedDescending(): Flow<List<MusicEntity>>

    fun getMusicsOrderedByDateAdded(ascending: Boolean): Flow<List<MusicEntity>> =
        if (ascending) getMusicsOrderedByDateAddedAscending()
        else getMusicsOrderedByDateAddedDescending()

    @Query("SELECT * FROM musicentity ORDER BY title ASC")
    fun getMusicsOrderedByTitleAscending(): Flow<List<MusicEntity>>

    @Query("SELECT * FROM musicentity ORDER BY title DESC")
    fun getMusicsOrderedByTitleDescending(): Flow<List<MusicEntity>>

    fun getMusicsOrderedByTitle(ascending: Boolean): Flow<List<MusicEntity>> =
        if (ascending) getMusicsOrderedByTitleAscending()
        else getMusicsOrderedByTitleDescending()

    @Query("SELECT * FROM musicentity ORDER BY artist ASC")
    fun getMusicsOrderedByArtistAscending(): Flow<List<MusicEntity>>

    @Query("SELECT * FROM musicentity ORDER BY artist DESC")
    fun getMusicsOrderedByArtistDescending(): Flow<List<MusicEntity>>

    fun getMusicsOrderedByArtist(ascending: Boolean): Flow<List<MusicEntity>> =
        if (ascending) getMusicsOrderedByArtistAscending()
        else getMusicsOrderedByArtistDescending()

}