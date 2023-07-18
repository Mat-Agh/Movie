package app.mat.movie.presentation.screen.scanner.components

import android.graphics.Bitmap
import android.graphics.Paint
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import app.mat.movie.R
import app.mat.movie.common.util.Roi
import app.mat.movie.presentation.theme.spacing

@Composable
fun TitleScanner(
    modifier: Modifier = Modifier,
    isScanningInProgress: Boolean = false,
    errorText: String? = null,
    onBitmapCaptured: (image: Bitmap, rotation: Float, roi: Roi?) -> Unit,
) {
    val backgroundColorCameraPreview = MaterialTheme.colorScheme.surface.copy(
        alpha = 0.5f
    )
    val resolutionSelector = ResolutionSelector.Builder()

    val context = LocalContext.current
    val imageCapture = remember {
        ImageCapture.Builder()
            .setResolutionSelector(
                resolutionSelector.build()
            )
            .setCaptureMode(
                ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
            )
            .build()
    }
    val executor = remember(context) {
        ContextCompat.getMainExecutor(context)
    }
    val borderColor = MaterialTheme.colorScheme.primary
    val errorTextSize = with(LocalDensity.current) {
        16.sp.toPx()
    }
    val errorTextPainter = remember {
        Paint().apply {
            color = Color.Red.toArgb()
            textSize = errorTextSize
            typeface =
                context.resources.getFont(
                    R.font.lato_black
                )
            textAlign = Paint.Align.CENTER
        }
    }
    var roi: Roi? = remember { null }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier.aspectRatio(
                3f / 4f
            )
        ) {
            CameraPreviewView(
                modifier = Modifier.fillMaxSize(),
                imageCapture = imageCapture
            )

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val rectHorizontalPadding = 32.dp.toPx()
                val rectWidth = size.width - 2 * rectHorizontalPadding
                val rectHeight = 48.dp.toPx()
                val cornerRadius = CornerRadius(
                    x = 16.dp.toPx(),
                    y = 16.dp.toPx()
                )

                val rect = Rect(
                    offset = Offset(
                        rectHorizontalPadding,
                        size.height * 0.33f - rectHeight / 2
                    ),
                    size = Size(rectWidth, rectHeight)
                )

                roi = Roi(
                    left = rect.left / size.width,
                    top = rect.top / size.height,
                    width = rect.width / size.width,
                    height = rect.height / size.height
                )
                val path = Path().apply {
                    val roundRect = RoundRect(
                        rect = rect,
                        cornerRadius = cornerRadius
                    )

                    addRoundRect(roundRect)
                }

                clipPath(
                    path,
                    clipOp = ClipOp.Difference
                ) {
                    drawRect(
                        SolidColor(
                            backgroundColorCameraPreview
                        )
                    )
                }

                drawRoundRect(
                    topLeft = rect.topLeft,
                    size = rect.size,
                    cornerRadius = cornerRadius,
                    color = borderColor,
                    style = Stroke(
                        width = 2.dp.toPx()
                    )
                )

                errorText?.let { text ->
                    drawIntoCanvas {
                        with(
                            it.nativeCanvas
                        ) {
                            drawText(
                                text,
                                rect.center.x,
                                rect.bottom + 16.dp.toPx() + errorTextSize / 2,
                                errorTextPainter
                            )
                        }
                    }
                }
            }
        }

        ScannerAcceptButton(
            modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .padding(
                    MaterialTheme.spacing.large
                )
                .size(
                    80.dp
                ),
            isScanningInProgress = isScanningInProgress,
            onClick = {
                imageCapture.takePicture(
                    executor,
                    object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(
                            image: ImageProxy
                        ) {
                            image.use { proxy ->
                                try {
                                    val rotation = image.imageInfo.rotationDegrees.toFloat()
                                    val bitmap = proxy.toBitmap()

                                    onBitmapCaptured(
                                        bitmap,
                                        rotation, roi
                                    )
                                } catch (_: IllegalArgumentException) {

                                }
                            }
                        }
                    }
                )
            }
        )
    }
}