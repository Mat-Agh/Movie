package app.mat.movie.presentation.screen.scanner

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.mat.movie.common.Parameter.Scan.Camera.SCAN_RESULT_KEY
import app.mat.movie.common.util.Roi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun AnimatedVisibilityScope.ScannerScreen(
    viewModel: ScannerScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBackClicked: () -> Unit = {
        navHostController.navigateUp()
    }
    val onAcceptClicked = {
        val result = uiState.scanResult

        if (result is ScanResult.Success) {
            navHostController.previousBackStackEntry?.savedStateHandle?.set(
                key = SCAN_RESULT_KEY,
                value = result.text
            )
        }
    }

    ScannerScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onBitmapCaptured = viewModel::onBitmapCaptured,
        onAcceptClicked = onAcceptClicked
    )
}

@OptIn(
    ExperimentalPermissionsApi::class,
)
@Composable
fun ScannerScreenContent(
    uiState: ScannerScreenUIState,
    onBackClicked: () -> Unit = {},
    onBitmapCaptured: (Bitmap, Float, Roi?) -> Unit,
    onAcceptClicked: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    val errorText = uiState.validationErrorResId?.let {
        stringResource(it)
    }
}