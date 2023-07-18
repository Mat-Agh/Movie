package app.mat.movie.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mat.movie.common.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    //region Variables
    private val _error: MutableSharedFlow<String?> = MutableSharedFlow(
        replay = 0
    )
    val error: StateFlow<String?> = _error.asSharedFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(
            10
        ),
        null
    )
    //endregion Variables

    //region Public Methods
    protected fun <T> onException(
        response: Resource.Exception<T>
    ) {
        viewModelScope.launch {
            _error.emit(
                response.exception.localizedMessage
            )
        }
    }

    protected fun <T> onError(
        response: Resource.Error<T>
    ) {
        viewModelScope.launch {
            _error.emit(
                response.error.statusMessage
            )
        }
    }
    //endregion Public Methods
}