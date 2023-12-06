package me.amirkazemzade.materialmusicplayer.domain.model

import android.net.Uri

data class MusicFile(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val filePath: String,
    val dateAdded: String,
    val dateModified: String,
    val duration: String,
    val genre: String? = null,
    val year: String? = null,
    val albumCover: ByteArray? = null,
    val uri: Uri,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MusicFile

        return id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + artist.hashCode()
        result = 31 * result + album.hashCode()
        result = 31 * result + filePath.hashCode()
        result = 31 * result + dateAdded.hashCode()
        result = 31 * result + dateModified.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + (genre?.hashCode() ?: 0)
        result = 31 * result + (year?.hashCode() ?: 0)
        result = 31 * result + (albumCover?.contentHashCode() ?: 0)
        return result
    }
}
