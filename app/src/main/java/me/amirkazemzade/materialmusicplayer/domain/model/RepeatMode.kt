package me.amirkazemzade.materialmusicplayer.domain.model

enum class RepeatMode(val numericValue: Int) {
    Off(0),
    One(1),
    All(2);

    companion object {
        fun fromNumericValue(numericValue: Int): RepeatMode {
            return when (numericValue) {
                in Int.MIN_VALUE..0 -> Off
                1 -> One
                in 2..Int.MAX_VALUE -> All
                else -> throw IllegalArgumentException("Invalid numeric value")
            }
        }
    }

    fun next(): RepeatMode {
        /*
        // ! Due to a bug in Player repeat mode, the [One] value is skipped.
        // ! Uncomment this section to use the original logic with [One] value included

        val newIndex = (entries.indexOf(this) + 1) % entries.size
        return entries[newIndex]
        */

        return if (this == Off) All else Off
    }
}

