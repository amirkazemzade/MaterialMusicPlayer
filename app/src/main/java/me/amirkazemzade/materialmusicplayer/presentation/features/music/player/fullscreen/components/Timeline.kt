package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import me.amirkazemzade.materialmusicplayer.presentation.common.MusicTimelineGeneratorMock
import me.amirkazemzade.materialmusicplayer.presentation.common.extensions.formatToMinutesAndSeconds
import me.amirkazemzade.materialmusicplayer.presentation.features.music.player.states.TimelineState
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme
import kotlin.math.max

@Composable
fun Timeline(
    timelineStateFlow: StateFlow<TimelineState>,
    onCurrentPositionChange: (value: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val timeline by timelineStateFlow.collectAsStateWithLifecycle()
    val duration = timeline.duration
    val currentPosition = timeline.currentPosition

    if (duration == null || currentPosition == null) {
        TimelineLoading(
            modifier = modifier.padding(horizontal = 10.dp),
        )
    } else {
        TimelineContent(
            modifier = modifier.padding(horizontal = 10.dp),
            durationMs = duration,
            currentPositionMs = currentPosition,
            onCurrentPositionChange = onCurrentPositionChange,
        )
    }
}

@Composable
private fun TimelineContent(
    durationMs: Long,
    currentPositionMs: Long,
    onCurrentPositionChange: (value: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sliderPosition = remember {
        mutableStateOf<Long?>(null)
    }
    val durationMsNonNegative = max(durationMs, 0)

    val value = sliderPosition.value ?: currentPositionMs

    Column(
        modifier = modifier,
    ) {
        TimeSlider(
            currentPosition = value.toFloat(),
            duration = durationMsNonNegative.toFloat(),
            onValueChange = {
                sliderPosition.value = it.toLong()
            },
            onValueChangeFinished = {
                onCurrentPositionChange(sliderPosition.value!!.toLong())
                sliderPosition.value = null
            },
        )
        Timestamps(
            sliderPosition = value,
            duration = durationMsNonNegative,
        )
    }
}

@Composable
private fun TimelineLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            contentAlignment = Alignment.Center
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Timestamps(
            sliderPosition = 0,
            duration = 0,
        )
    }
}

@Composable
private fun TimeSlider(
    currentPosition: Float,
    duration: Float,
    onValueChange: (value: Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Slider(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp),
        value = currentPosition,
        valueRange = 0f..duration,
        onValueChange = onValueChange,
        onValueChangeFinished = onValueChangeFinished,
    )
}

@Composable
private fun Timestamps(
    sliderPosition: Long,
    duration: Long,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        val currentPositionText = sliderPosition.formatToMinutesAndSeconds()
        val durationText = duration.formatToMinutesAndSeconds()

        Text(text = currentPositionText)
        Text(text = durationText)
    }
}

@Preview
@Composable
private fun TimelinePreview() {
    val timelineMock = MusicTimelineGeneratorMock()
    val position = timelineMock.state.collectAsState()
    MaterialMusicPlayerTheme {
        Surface {
            TimelineContent(
                durationMs = timelineMock.duration,
                currentPositionMs = position.value,
                onCurrentPositionChange = {
                    timelineMock.setPosition(it)
                }
            )
        }
    }
}