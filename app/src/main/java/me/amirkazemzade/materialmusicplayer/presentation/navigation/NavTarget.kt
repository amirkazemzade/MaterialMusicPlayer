package me.amirkazemzade.materialmusicplayer.presentation.navigation

sealed class NavTarget(val label: String) {
    data object MusicList : NavTarget(ModuleRoutes.MusicList.label)
    data class MusicPlayer(val encodeMusicUri: String) : NavTarget(
        "${ModuleRoutes.MusicPlayer.label}/$encodeMusicUri"
    )
}

enum class ModuleRoutes(val label: String) {
    MusicList("MusicList"),
    MusicPlayer("MusicPlayer")
}
