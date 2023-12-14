package me.amirkazemzade.materialmusicplayer.presentation.features.music.player.fullscreen.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.amirkazemzade.materialmusicplayer.presentation.common.MusicTimelineGeneratorMock
import me.amirkazemzade.materialmusicplayer.presentation.common.formatToMinutesAndSeconds
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@Composable
fun Timeline(
    currentPositionMs: Long?,
    durationMs: Long?,
    onCurrentPositionChange: (value: Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    val sliderPosition = rememberSaveable {
        mutableStateOf<Long?>(null)
    }

    if (currentPositionMs == null || durationMs == null) {
        LinearProgressIndicator()
    } else {
        Column(
            modifier = modifier.padding(horizontal = 10.dp),
        ) {
            val value = sliderPosition.value ?: currentPositionMs
            TimeSlider(
                currentPosition = value.toFloat(),
                duration = durationMs.toFloat(),
                onValueChange = {
                    sliderPosition.value = it.toLong()
                },
                onValueChangeFinished = {
                    onCurrentPositionChange(sliderPosition.value!!)
                    sliderPosition.value = null
                },
            )
            Timestamps(
                sliderPosition = value,
                duration = durationMs,
            )
        }
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
        modifier = modifier.fillMaxWidth().height(20.dp),
        value = currentPosition,
        valueRange = 0f..duration,
        onValueChange = onValueChange,
        onValueChangeFinished = onValueChangeFinished
    )
}

@Composable
private fun Timestamps(
    sliderPosition: Long,
    duration: Long,
) {
    Row(
        modifier = Modifier.padding(horizontal = 6.dp).fillMaxWidth(),
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
            Timeline(
                currentPositionMs = position.value,
                durationMs = timelineMock.duration,
                onCurrentPositionChange = {
                    timelineMock.setPosition(it)
                }
            )
        }
    }
}