package me.amirkazemzade.materialmusicplayer

import android.net.Uri
import br.com.colman.kotest.FunSpec
import br.com.colman.kotest.KotestRunnerAndroid
import io.kotest.matchers.shouldBe
import me.amirkazemzade.materialmusicplayer.data.db.MusicDatabase
import me.amirkazemzade.materialmusicplayer.data.db.dao.MusicDao
import me.amirkazemzade.materialmusicplayer.data.db.dao.QueueDao
import me.amirkazemzade.materialmusicplayer.data.db.entities.music.MusicEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueDataEntity
import me.amirkazemzade.materialmusicplayer.data.db.entities.queue.QueueItemEntity
import org.junit.runner.RunWith

@RunWith(KotestRunnerAndroid::class)
class QueueDaoTestK : FunSpec({
    lateinit var database: MusicDatabase
    lateinit var musicDao: MusicDao
    lateinit var queueDao: QueueDao

    beforeTest {

        database = buildMusicDatabase()

        musicDao = database.musicDao
        queueDao = database.queueDao
    }

    afterTest {
        database.close()
    }

    test("Queue Data") {
        // Insert
        val queueData = QueueDataEntity(id = 7, currentIndex = 10, currentPositionMs = 200)
        queueDao.insertQueueData(queueData)

        queueDao.getQueueData() shouldBe queueData

        // Upsert
        val updatingQueueData =
            QueueDataEntity(id = 7, currentIndex = 12, currentPositionMs = 300)
        queueDao.upsertQueueData(updatingQueueData)

        queueDao.getQueueData() shouldBe updatingQueueData

        // Delete
        queueDao.deleteQueueData()

        queueDao.getQueueData() shouldBe null
    }

    test("Queue Item") {
        // Pre-Insertion
        val music = createMusic(1542)
        musicDao.upsertMusics(listOf(music))

        // Insert
        val queueItem = QueueItemEntity(musicId = music.id, order = 8249, id = 8727)
        queueDao.insertQueueItem(queueItem)

        val insertedQueueItem = queueDao.getQueueItem(queueItem.id)
        val insertedQueueItemId = queueDao.getQueueItem(queueItem.order)

        insertedQueueItem shouldBe queueItem
        insertedQueueItemId shouldBe queueItem.id

        //
    }

    test("Queue Items") {
        // Pre-Insertion
        val musics = buildList {
            for (i in 0..5) {
                add(createMusic(i.toLong()))
            }
        }
        musicDao.upsertMusics(musics)

        // Insert Queue Items
        val queueItems = listOf(
            QueueItemEntity(musicId = musics[0].id, order = 1, id = 2502),
            QueueItemEntity(musicId = musics[1].id, order = 2, id = 1630),
            QueueItemEntity(musicId = musics[2].id, order = 3, id = 9987),
            QueueItemEntity(musicId = musics[3].id, order = 4, id = 1533),
            QueueItemEntity(musicId = musics[4].id, order = 5, id = 5875),
            QueueItemEntity(musicId = musics[1].id, order = 6, id = 8232),
        )
        queueDao.insertQueueItems(queueItems)

        // Get Queue Items Related Values
        val insertedQueueItems = queueDao.getQueueItems()

        // Assertion
        insertedQueueItems shouldBe queueItems.sortedBy { it.id }
    }
})

private fun createMusic(musicId: Long) = MusicEntity(
    id = musicId,
    title = null,
    artist = null,
    album = null,
    filePath = "/music/$musicId",
    dateAdded = null,
    dateModified = null,
    duration = null,
    genre = null,
    year = null,
    artworkThumbnail = null,
    uri = Uri.EMPTY,
)

