package me.amirkazemzade.materialmusicplayer.domain.repository

import android.content.Context
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.amirkazemzade.materialmusicplayer.data.repository.MusicRepository
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.musicProjection
import me.amirkazemzade.materialmusicplayer.domain.model.toMusicFile

class MusicRepositoryImpl(
    private val context: Context
) : MusicRepository {
    override suspend fun getMusicList(sortOrder: String?): List<MusicFile> = withContext(
        Dispatchers.IO
    ) {
        // Query the MediaStore.Audio.Media table.
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            musicProjection,
            null,
            null,
            sortOrder
        )

        // Check if the cursor is not null and has at least one row.
        if (cursor != null && cursor.moveToFirst()) {
            // Create a list to store the music files.
            val musicFiles = mutableListOf<MusicFile>()

            // Iterate over the cursor and add each music file to the list.
            do {
                musicFiles.add(cursor.toMusicFile())
            } while (cursor.moveToNext())

            // Close the cursor.
            cursor.close()

            // Return the list of music files.
            return@withContext musicFiles
        } else {
            // No music files found.
            return@withContext emptyList()
        }
    }
}
