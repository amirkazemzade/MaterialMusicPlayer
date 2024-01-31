package me.amirkazemzade.materialmusicplayer.presentation.features.music.list.components

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.common.math.IntMath.pow
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.domain.model.DefaultSortType
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.presentation.common.extensions.plus
import me.amirkazemzade.materialmusicplayer.presentation.common.extensions.withOutBottom
import me.amirkazemzade.materialmusicplayer.presentation.features.music.components.SortControllers
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicListContent(
    contentPadding: PaddingValues,
    partialLoading: Boolean,
    sortOrder: SortOrder,
    sortType: DefaultSortType,
    musics: ImmutableList<MusicFile>,
    onSortByOrder: (sortOrder: SortOrder) -> Unit,
    onSortByType: (sortType: DefaultSortType) -> Unit,
    onItemClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hazeState by remember {
        mutableStateOf(HazeState())
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            ActionBar(
                modifier = Modifier
                    .hazeChild(hazeState),
                contentPadding = contentPadding,
                selectedSortOrder = sortOrder,
                selectedSortType = sortType,
                onSortByOrder = onSortByOrder,
                onSortByType = onSortByType,
            )
        }
    ) { internalContentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    MaterialTheme.colorScheme.surface
                ),
            contentPadding = internalContentPadding + contentPadding,
        ) {
            itemsIndexed(musics, key = { _, item -> item.id }) { index, music ->
                MusicListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(animationSpec = tween(700)),
                    music = music,
                    onClick = { onItemClick(index) },
                )
            }
        }

    }
}

@Composable
private fun ActionBar(
    contentPadding: PaddingValues,
    selectedSortOrder: SortOrder,
    selectedSortType: DefaultSortType,
    onSortByOrder: (sortOrder: SortOrder) -> Unit,
    onSortByType: (sortType: DefaultSortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sortTypeTitleMap = mapOf(
        DefaultSortType.DATE_ADDED to stringResource(R.string.date_added),
        DefaultSortType.TITLE to stringResource(R.string.title),
        DefaultSortType.ARTIST to stringResource(R.string.artist),
    )

    SortControllers(
        modifier = modifier
            .padding(contentPadding.withOutBottom())
            .fillMaxWidth(),
        sortTypes = DefaultSortType.entries.toImmutableList(),
        selectedSortOrder = selectedSortOrder,
        selectedSortType = selectedSortType,
        onSortByOrder = onSortByOrder,
        onSortByType = onSortByType,
        sortTypeTitle = { sortType ->
            sortTypeTitleMap[sortType]!!
        },
    )
}

@Preview
@Composable
private fun PreviewMusicList() {
    val musicList = buildList {
        repeat(10) { index ->
            addAll(
                listOf(
                    MusicFile(
                        id = 33 * pow(10, index).toLong(),
                        title = "2step (feat. Lil Baby)",
                        artist = "Ed Sheeran",
                        album = "2step (feat. Lil Baby)",
                        filePath = "/storage/emulated/0/Download/01 - 2 step (feat.Lil Baby).mp3",
                        dateAdded = "1696781534",
                        dateModified = "1696781534",
                        duration = "163500",
                        genre = "Pop",
                        year = "2022",
                        uri = Uri.EMPTY,
                    ),
                    MusicFile(
                        id = 34 * pow(10, index).toLong(),
                        title = "Bad For Me(feat.Teddy Swims)",
                        artist = "Meghan Trainor",
                        album = "Bad For Me(feat.Teddy Swims)",
                        filePath = "/storage/emulated/0/Download/01 - " + "Bad For Me(feat.Teddy Swims).mp3",
                        dateAdded = "1696781534",
                        dateModified = "1696781535",
                        duration = "213342",
                        genre = null,
                        year = "2022",
                        uri = Uri.EMPTY,
                    ),
                    MusicFile(
                        id = 35 * pow(10, index).toLong(),
                        title = "Left and Right(Feat.Jung Kook of BTS)",
                        artist = "Charlie Puth",
                        album = "Left and Right(Feat.Jung Kook of BTS)",
                        filePath = "/storage/emulated/0/Download/01 - " + "Left and Right(Feat.Jung Kook of BTS).mp3",
                        dateAdded = "1696781535",
                        dateModified = "1696781536",
                        duration = "154514",
                        genre = "Pop",
                        year = "2022",
                        uri = Uri.EMPTY,
                    ),
                ),
            )
        }
    }.toImmutableList()

    val sortOrder = remember {
        mutableStateOf(SortOrder.ASC)
    }

    val sortType = remember {
        mutableStateOf(DefaultSortType.DATE_ADDED)
    }

    MaterialMusicPlayerTheme {
        Scaffold {
            MusicListContent(
                contentPadding = PaddingValues(),
                partialLoading = true,
                sortOrder = sortOrder.value,
                sortType = sortType.value,
                musics = musicList,
                onSortByOrder = { newSortOrder ->
                    sortOrder.value = newSortOrder
                },
                onSortByType = { newSortType ->
                    sortType.value = newSortType
                },
                onItemClick = {},
                modifier = Modifier.padding(it),
            )
        }
    }
}
