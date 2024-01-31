package me.amirkazemzade.materialmusicplayer.presentation.common.modifiers

import androidx.compose.ui.Modifier

fun Modifier.applyIf(condition: Boolean, modifier: Modifier): Modifier {
    val conditionedModifier = if (condition) modifier else Modifier
    return this then conditionedModifier
}
