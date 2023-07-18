package app.mat.movie.presentation.view

import androidx.lifecycle.viewModelScope
import app.mat.movie.common.type.NetworkStatus
import app.mat.movie.common.type.SnackBarEvent
import app.mat.movie.common.util.ImageUrlParser
import app.mat.movie.common.util.NetworkStatusTracker
import app.mat.movie.data.repository.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkStatusTracker: NetworkStatusTracker,
    private val configRepository: ConfigRepository
) : BaseViewModel() {
    //region Variables
    private val connectionStatus = networkStatusTracker.connectionStatus

    @OptIn(ExperimentalCoroutinesApi::class)
    val networkSnackBarEvent: StateFlow<SnackBarEvent?> = connectionStatus.mapLatest { status ->
        when (status) {
            NetworkStatus.Connected -> SnackBarEvent.NetworkConnected
            NetworkStatus.Disconnected -> SnackBarEvent.NetworkDisconnected
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )

    private val sameBottomBarRouteChannel: Channel<String> = Channel()

    val sameBottomBarRoute: Flow<String> = sameBottomBarRouteChannel.receiveAsFlow().flowOn(
        Dispatchers.Main.immediate
    )

    val imageUrlParser: StateFlow<ImageUrlParser?> = configRepository.getImageUrlParser().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )
    //endregion Variables

    //region Public Methods
    fun updateLocale() {
        configRepository.updateLocale()
    }

    fun onSameRouteSelected(
        route: String
    ) {
        viewModelScope.launch(
            Dispatchers.Main.immediate
        ) {
            sameBottomBarRouteChannel.send(
                route
            )
        }
    }
    //endregion Public Methods
}