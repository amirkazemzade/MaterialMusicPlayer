package me.amirkazemzade.materialmusicplayer.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import me.amirkazemzade.materialmusicplayer.R
import me.amirkazemzade.materialmusicplayer.presentation.ui.theme.MaterialMusicPlayerTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReadAudioPermissionHandler(
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {
    val readAudioPermissionState = rememberPermissionState(
        android.Manifest.permission.READ_MEDIA_AUDIO
    )

    if (readAudioPermissionState.status.isGranted) {
        content(modifier)
    } else {
        ReadAudioPermissionRequestPage(
            modifier = modifier,
            onRequestPermission = { readAudioPermissionState.launchPermissionRequest() }
        )
    }
}

@Composable
private fun ReadAudioPermissionRequestPage(
    modifier: Modifier,
    onRequestPermission: () -> Unit
) {
    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.audio_permission_request),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(16.dp))
            Button(onClick = onRequestPermission) {
                Text(stringResource(R.string.request_permission))
            }
        }
    }
}

@Preview
@Composable
fun ReadAudioPermissionRequestPagePreview() {
    MaterialMusicPlayerTheme {
        ReadAudioPermissionRequestPage(
            modifier = Modifier,
            onRequestPermission = {}
        )
    }
}
