package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.queue.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.toImmutableList
import me.amirkazemzade.materialmusicplayer.domain.model.SortOrder
import me.amirkazemzade.materialmusicplayer.domain.model.SortTypeWithCustom
import me.amirkazemzade.materialmusicplayer.presentation.features.music.components.SortControllers

@Composable
fun OrderingTopAppBar(
    modifier: Modifier = Modifier,
) {
    // TODO: add functionality for ordering top app bar
    SortControllers(
        modifier = modifier,
        sortTypes = SortTypeWithCustom.entries.toImmutableList(),
        sortTypeTitle = { it.name },
        selectedSortOrder = SortOrder.ASC,
        selectedSortType = SortTypeWithCustom.CUSTOM_TYPE,
        onSortByOrder = {},
        onSortByType = {},
    )
}
