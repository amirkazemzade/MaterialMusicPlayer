package me.amirkazemzade.materialmusicplayer.domain.model

interface SortType

enum class DefaultSortType : SortType {
    DATE_ADDED,
    TITLE,
    ARTIST,
}

enum class SortTypeWithCustom : SortType {
    CUSTOM_TYPE,
    DATE_ADDED,
    TITLE,
    ARTIST,
}