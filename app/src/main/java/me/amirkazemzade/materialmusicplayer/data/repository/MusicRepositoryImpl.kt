package me.amirkazemzade.materialmusicplayer.data.repository

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import io.sentry.Sentry
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.amirkazemzade.materialmusicplayer.data.mapper.toMusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.StatusGeneric
import me.amirkazemzade.materialmusicplayer.domain.repository.MusicRepository

val MUSIC_PROJECTION =
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
                MediaStore.Audio.Media.XMP,
                MediaStore.Audio.Media.YEAR,
            ),
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            add(MediaStore.Audio.Media.GENRE)
        }
    }.toTypedArray()


class MusicRepositoryImpl(
    private val context: Context,
) : MusicRepository {
    override fun getMusicList(
        sortOrder: String?,
        ascending: Boolean,
    ): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> = flow {
        emit(StatusGeneric.Loading())

        // Query the MediaStore.Audio.Media table.
        val cursor =
            context.contentResolver.query(
                /* uri = */             MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                /* projection = */      MUSIC_PROJECTION,
                /* selection = */       "${MediaStore.Audio.Media.IS_MUSIC} != 0",
                /* selectionArgs = */   null,
                /* sortOrder = */       "$sortOrder ${if (ascending) "ASC" else "DESC"}",
            )

        // Check if the cursor is not null and has at least one row.
        if (cursor != null && cursor.moveToFirst()) {
            // Create a list to store the music files.
            val musicFiles = mutableListOf<MusicFile>()
            var errorRate = 0
            // Iterate over the cursor and add each music file to the list.
            do {
                try {
                    musicFiles.add(cursor.toMusicFile(context))
                } catch (e: Exception) {
                    errorRate++
                    Sentry.captureException(e)
                    Sentry.captureMessage("$cursor")
                }
            } while (cursor.moveToNext())

            // Close the cursor.
            cursor.close()

            // Return the list of music files.
            emit(StatusGeneric.Success(musicFiles.toImmutableList(), partialMessage = errorRate))
        } else {
            // No music files found.
            emit(StatusGeneric.Success(persistentListOf()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMusicListOrderedByDateAdded(ascending: Boolean): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> {
        return getMusicList(
            sortOrder = MediaStore.Audio.Media.DATE_ADDED,
            ascending = ascending,
        )
    }

    override fun getMusicListOrderedByTitle(ascending: Boolean): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> {
        return getMusicList(
            sortOrder = MediaStore.Audio.Media.TITLE,
            ascending = ascending,
        )
    }

    override fun getMusicListOrderedByArtist(ascending: Boolean): Flow<StatusGeneric<ImmutableList<MusicFile>, Int>> {
        return getMusicList(
            sortOrder = MediaStore.Audio.Media.ARTIST,
            ascending = ascending,
        )
    }
}

