package me.amirkazemzade.materialmusicplayer.presentation.features.music.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.domain.model.SortType
import me.amirkazemzade.materialmusicplayer.presentation.common.materialmusicicons.MaterialMusicIcons
import me.amirkazemzade.materialmusicplayer.presentation.common.materialmusicicons.icons.SortAscending
import me.amirkazemzade.materialmusicplayer.presentation.common.materialmusicicons.icons.SortDescending

@Composable
fun <T : SortType> SortControllers(
    sortTypes: ImmutableList<T>,
    sortTypeTitle: (sortType: T) -> String,
    selectedSortOrder: SortOrder,
    selectedSortType: T,
    onSortByOrder: (sortOrder: SortOrder) -> Unit,
    onSortByType: (sortType: T) -> Unit,
    modifier: Modifier = Modifier,
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .padding(start = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (selectedSortOrder.isAscending) {
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

        TextButton(onClick = { dropdownExpanded = true }) {
            Text(text = sortTypeTitle(selectedSortType))
        }
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
            offset = DpOffset(x = 48.dp, y = 0.dp),
        ) {
            for (sortType in sortTypes) {
                DropdownMenuItem(
                    text = { Text(text = sortTypeTitle(sortType)) },
                    onClick = {
                        onSortByType(sortType)
                        dropdownExpanded = false
                    },
                )
            }
        }
    }
}
