package app.mat.movie.presentation.screen.scanner.components

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import app.mat.movie.common.util.getCameraProvider
import timber.log.Timber

@Composable
fun CameraPreviewView(
    imageCapture: ImageCapture,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val resolutionSelector = ResolutionSelector.Builder()
    val preview = remember {
        Preview.Builder()
            .setResolutionSelector(
                resolutionSelector.build()
            )
            .build()
    }
    val cameraSelector = remember {
        CameraSelector.Builder()
            .requireLensFacing(
                CameraSelector.LENS_FACING_BACK
            )
            .build()
    }
    val previewView = remember {
        PreviewView(
            context
        ).apply {
            scaleType = PreviewView.ScaleType.FIT_CENTER
        }
    }
    LaunchedEffect(Unit) {
        val cameraProvider = context.getCameraProvider()
        with(cameraProvider) {
            try {
                unbindAll()
                bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Timber.tag(
                    "CameraPreview"
                ).e(
                    e,
                    "Use case binding failed"
                )
            }
        }
        preview.setSurfaceProvider(
            previewView.surfaceProvider
        )
    }
    AndroidView(
        {
            previewView
        },
        modifier = modifier
    )
}