package app.mat.movie.presentation.screen.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.show.GetAiringTodayShowsUseCase
import app.mat.movie.domain.userCase.show.GetDiscoverAllShowsUseCase
import app.mat.movie.domain.userCase.show.GetFavoritesShowsUseCase
import app.mat.movie.domain.userCase.show.GetOnTheAirShowsUseCase
import app.mat.movie.domain.userCase.show.GetRecentlyBrowsedShowsUseCase
import app.mat.movie.domain.userCase.show.GetTopRatedShowsUseCase
import app.mat.movie.domain.userCase.show.GetTrendingShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ShowScreenViewModel @Inject constructor(
    getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    private val getOnTheAirShowsUseCase: GetOnTheAirShowsUseCase,
    private val getDiscoverAllShowsUseCase: GetDiscoverAllShowsUseCase,
    private val getTopRatedShowsUseCase: GetTopRatedShowsUseCase,
    private val getTrendingShowsUseCase: GetTrendingShowsUseCase,
    private val getAiringTodayShowsUseCase: GetAiringTodayShowsUseCase,
    private val getFavoritesShowsUseCase: GetFavoritesShowsUseCase,
    private val getRecentlyBrowsedShowsUseCase: GetRecentlyBrowsedShowsUseCase
) : ViewModel() {
    //region Variables
    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    private val tvShowsState: StateFlow<TvShowsState> = deviceLanguage.mapLatest { deviceLanguage ->
        TvShowsState(
            onTheAir = getOnTheAirShowsUseCase(
                deviceLanguage,
                true
            ).cachedIn(
                viewModelScope
            ),
            discover = getDiscoverAllShowsUseCase(
                deviceLanguage
            ).cachedIn(
                viewModelScope
            ),
            topRated = getTopRatedShowsUseCase(
                deviceLanguage
            ).cachedIn(
                viewModelScope
            ),
            trending = getTrendingShowsUseCase(
                deviceLanguage
            ).cachedIn(
                viewModelScope
            ),
            airingToday = getAiringTodayShowsUseCase(
                deviceLanguage
            ).cachedIn(
                viewModelScope
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(
            10
        ),
        TvShowsState.default
    )

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    val uiState: StateFlow<TvShowScreenUIState> = tvShowsState.mapLatest { tvShowsState ->
        TvShowScreenUIState(
            tvShowsState = tvShowsState,
            favorites = getFavoritesShowsUseCase(),
            recentlyBrowsed = getRecentlyBrowsedShowsUseCase()
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        TvShowScreenUIState.default
    )
    //endregion Variables
}