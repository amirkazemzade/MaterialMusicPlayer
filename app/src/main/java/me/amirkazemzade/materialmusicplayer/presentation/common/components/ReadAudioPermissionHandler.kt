package me.amirkazemzade.materialmusicplayer.presentation.common.components

import android.os.Build
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
    content: @Composable (Modifier) -> Unit,
) {
    val (requiredPermission, permissionRequestTextId) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Pair(android.Manifest.permission.READ_MEDIA_AUDIO, R.string.audio_permission_request)
        } else {
            Pair(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                R.string.storage_permission_request,
            )
        }
    val readAudioPermissionState =
        rememberPermissionState(
            requiredPermission,
        )

    if (readAudioPermissionState.status.isGranted) {
        content(modifier)
    } else {
        ReadAudioPermissionRequestPage(
            modifier = modifier,
            requestPermissionTextId = permissionRequestTextId,
            onRequestPermission = { readAudioPermissionState.launchPermissionRequest() },
        )
    }
}

@Composable
private fun ReadAudioPermissionRequestPage(
    requestPermissionTextId: Int,
    onRequestPermission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier =
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(requestPermissionTextId),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRequestPermission) {
                Text(stringResource(R.string.request_permission))
            }
        }
    }
}

@Preview
@Composable
private fun ReadAudioPermissionRequestPagePreview() {
    MaterialMusicPlayerTheme {
        ReadAudioPermissionRequestPage(
            modifier = Modifier,
            requestPermissionTextId = R.string.audio_permission_request,
            onRequestPermission = {},
        )
    }
}

@Preview
@Composable
private fun ReadStoragePermissionRequestPagePreview() {
    MaterialMusicPlayerTheme {
        ReadAudioPermissionRequestPage(
            modifier = Modifier,
            requestPermissionTextId = R.string.storage_permission_request,
            onRequestPermission = {},
        )
    }
}
