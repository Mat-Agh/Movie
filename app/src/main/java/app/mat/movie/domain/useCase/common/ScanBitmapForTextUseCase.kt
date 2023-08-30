package app.mat.movie.domain.useCase.common

import android.graphics.Bitmap
import app.mat.movie.common.util.Roi
import app.mat.movie.common.util.TextRecognitionHelper
import kotlinx.coroutines.flow.Flow

interface ScanBitmapForTextUseCase {
    operator fun invoke(
        bitmap: Bitmap,
        rotation: Float,
        roi: Roi?
    ): Flow<TextRecognitionHelper.ScanState>
}