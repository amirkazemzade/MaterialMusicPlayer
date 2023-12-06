package me.amirkazemzade.materialmusicplayer.data.repository

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.amirkazemzade.materialmusicplayer.data.mapper.toMusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository

class MusicRepositoryImpl(
    private val context: Context,
) : MusicRepository {
    override suspend fun getMusicList(sortOrder: String?): ImmutableList<MusicFile> =
        withContext(
            Dispatchers.IO,
        ) {
            val musicProjection =
                buildList {
                    addAll(
                        listOf(
                            MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.DATE_ADDED,
                            MediaStore.Audio.Media.DATE_MODIFIED,
                            MediaStore.Audio.Media.DURATION,
                            MediaStore.Audio.Media.YEAR,
                        ),
                    )
                    if (Build.VERSION.SDK_INT >= 30) {
                        add(MediaStore.Audio.Media.GENRE)
                    }
                }.toTypedArray()

            // Query the MediaStore.Audio.Media table.
            val cursor =
                context.contentResolver.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    musicProjection,
                    null,
                    null,
                    sortOrder,
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
                return@withContext musicFiles.toImmutableList()
            } else {
                // No music files found.
                return@withContext persistentListOf()
            }
        }
}
