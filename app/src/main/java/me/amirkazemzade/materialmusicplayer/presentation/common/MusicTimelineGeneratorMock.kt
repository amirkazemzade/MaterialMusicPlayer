package me.amirkazemzade.materialmusicplayer.presentation.common

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class MusicTimelineGeneratorMock(
    val duration: Long = 100L,
    private val delay: Long = 500L,
) {
    private val _state = MutableStateFlow(0L)
    val state: StateFlow<Long>
        get() = _state.asStateFlow()

    fun setPosition(value: Long) {
        _state.value = value
    }

    init {
        GlobalScope.launch {
            while (true) {
                delay(delay)
                _state.value = if (state.value < duration) state.value + 1 else 0L
            }
        }
    }
}