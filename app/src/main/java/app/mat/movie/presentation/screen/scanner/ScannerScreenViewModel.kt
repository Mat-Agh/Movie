package app.mat.movie.presentation.screen.scanner

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import app.mat.movie.common.util.Roi
import app.mat.movie.common.util.TextRecognitionHelper
import app.mat.movie.domain.useCase.common.ScanBitmapForTextUseCase
import app.mat.movie.presentation.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerScreenViewModel @Inject constructor(
    private val scanBitmapForTextUseCase: ScanBitmapForTextUseCase
) : BaseViewModel() {
    //region Variables
    private val scanState: MutableStateFlow<TextRecognitionHelper.ScanState> = MutableStateFlow(
        TextRecognitionHelper.ScanState.Idle
    )

    val uiState: StateFlow<ScannerScreenUIState> = scanState.map { scanState ->
        val isScanInProgress = scanState is TextRecognitionHelper.ScanState.Loading
        val scanResult = when (scanState) {
            is TextRecognitionHelper.ScanState.Success -> ScanResult.Success(
                scanState.text
            )

            is TextRecognitionHelper.ScanState.Error -> ScanResult.Error(
                scanState.message
            )

            else -> null
        }
        val validationErrorResId = when (scanState) {
            is TextRecognitionHelper.ScanState.InvalidText -> scanState.errorResId
            else -> null
        }
        ScannerScreenUIState(
            scanningInProgress = isScanInProgress,
            scanResult = scanResult,
            validationErrorResId = validationErrorResId
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ScannerScreenUIState.default
    )
    //endregion

    //region Public Methods
    fun onBitmapCaptured(
        bitmap: Bitmap,
        rotation: Float,
        roi: Roi?
    ) {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            scanBitmapForTextUseCase(
                bitmap,
                rotation,
                roi
            ).collect { state ->
                scanState.emit(
                    state
                )
            }
        }
    }
    //endregion Public Methods
}