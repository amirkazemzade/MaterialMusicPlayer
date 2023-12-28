package me.amirkazemzade.materialmusicplayer.presentation.features.music.list.components

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.google.common.math.IntMath.pow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.domain.model.MusicFile
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.domain.model.SortType
import me.amirkazemzade.materialmusicplayer.presentation.common.extensions.withOutBottom
import me.amirkazemzade.materialmusicplayer.presentation.common.extensions.withOutTop
import me.amirkazemzade.materialmusicplayer.presentation.common.materialmusicicons.MaterialMusicIcons
import me.amirkazemzade.materialmusicplayer.presentation.common.materialmusicicons.icons.SortAscending
import me.amirkazemzade.materialmusicplayer.presentation.common.materialmusicicons.icons.SortDescending
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@Composable
fun MusicListContent(
    contentPadding: PaddingValues,
    partialLoading: Boolean,
    sortOrder: SortOrder,
    sortType: SortType,
    musics: ImmutableList<MusicFile>,
    onSortByOrder: (sortOrder: SortOrder) -> Unit,
    onSortByType: (sortType: SortType) -> Unit,
    onItemClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ActionBar(
            modifier = Modifier.padding(
                contentPadding.withOutBottom()
            ),
            sortOrder = sortOrder,
            sortType = sortType,
            onSortByOrder = onSortByOrder,
            onSortByType = onSortByType,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        ) {
            if (partialLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = contentPadding.withOutTop(),
        ) {
            itemsIndexed(musics, key = { _, item -> item.id }) { index, music ->
                MusicListItem(
                    modifier = Modifier.fillMaxWidth(),
                    music = music,
                    onClick = { onItemClick(index) },
                )
            }
        }
    }
}

@Composable
private fun ActionBar(
    sortOrder: SortOrder,
    sortType: SortType,
    onSortByOrder: (sortOrder: SortOrder) -> Unit,
    onSortByType: (sortType: SortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .padding(start = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (sortOrder.isAscending) {
            IconButton(onClick = { onSortByOrder(SortOrder.DESC) }) {
                Icon(
                    imageVector = MaterialMusicIcons.SortAscending,
                    contentDescription = stringResource(R.string.ascending_order),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        } else {
            IconButton(onClick = { onSortByOrder(SortOrder.ASC) }) {
                Icon(
                    imageVector = MaterialMusicIcons.SortDescending,
                    contentDescription = stringResource(R.string.descending_sort),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
        val sortTypeText = when (sortType) {
            SortType.DATE_ADDED -> stringResource(R.string.date_added)
            SortType.TITLE -> stringResource(R.string.title)
            SortType.ARTIST -> stringResource(R.string.artist)
        }

        TextButton(onClick = { dropdownExpanded = true }) {
            Text(text = sortTypeText)
        }
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
            offset = DpOffset(x = 48.dp, y = 0.dp),
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.date_added)) },
                onClick = {
                    onSortByType(SortType.DATE_ADDED)
                    dropdownExpanded = false
                },
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.title)) },
                onClick = {
                    onSortByType(SortType.TITLE)
                    dropdownExpanded = false
                },
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.artist)) },
                onClick = {
                    onSortByType(SortType.ARTIST)
                    dropdownExpanded = false
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMusicList() {
    val musicList =
        buildList {
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
                            filePath =
                            "/storage/emulated/0/Download/01 - " +
                                    "Bad For Me(feat.Teddy Swims).mp3",
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
                            filePath =
                            "/storage/emulated/0/Download/01 - " +
                                    "Left and Right(Feat.Jung Kook of BTS).mp3",
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
        mutableStateOf(SortType.DATE_ADDED)
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
