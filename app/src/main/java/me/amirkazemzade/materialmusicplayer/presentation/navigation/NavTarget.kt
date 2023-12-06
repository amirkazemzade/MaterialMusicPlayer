package me.amirkazemzade.materialmusicplayer.presentation.navigation

sealed class NavTarget(val label: String) {
    data object Music : NavTarget(ModuleRoutes.MusicList.label)
}

enum class ModuleRoutes(val label: String) {
    MusicList("Music"),
}
