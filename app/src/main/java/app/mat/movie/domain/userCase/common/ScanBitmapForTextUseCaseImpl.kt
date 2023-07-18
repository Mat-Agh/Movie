package app.mat.movie.domain.userCase.common

import android.graphics.Bitmap
import app.mat.movie.common.util.Roi
import app.mat.movie.common.util.TextRecognitionHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScanBitmapForTextUseCaseImpl @Inject constructor(
    private val textRecognitionHelper: TextRecognitionHelper
) : ScanBitmapForTextUseCase {
    override operator fun invoke(
        bitmap: Bitmap,
        rotation: Float,
        roi: Roi?
    ): Flow<TextRecognitionHelper.ScanState> = textRecognitionHelper.scanTextFromBitmap(
        bitmap,
        rotation, roi
    )
}