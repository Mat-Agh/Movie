package app.mat.movie.presentation.screen.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.movie.GetDiscoverAllMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetFavoritesMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetNowPlayingMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetRecentlyBrowsedMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetTopRatedMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetTrendingMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieScreenViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getDiscoverAllMoviesUseCase: GetDiscoverAllMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val favoritesMoviesUseCase: GetFavoritesMoviesUseCase,
    private val getRecentlyBrowsedMoviesUseCase: GetRecentlyBrowsedMoviesUseCase
) : ViewModel() {
    //region Variables
    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    private val moviesState: StateFlow<MoviesState> = deviceLanguage.mapLatest { deviceLanguage ->
        MoviesState(
            nowPlaying = getNowPlayingMoviesUseCase(
                deviceLanguage,
                true
            ).cachedIn(
                viewModelScope
            ),
            discover = getDiscoverAllMoviesUseCase(
                deviceLanguage
            ).cachedIn(
                viewModelScope
            ),
            upcoming = getUpcomingMoviesUseCase(
                deviceLanguage
            ).cachedIn(
                viewModelScope
            ),
            trending = getTrendingMoviesUseCase(
                deviceLanguage
            ).cachedIn(
                viewModelScope

            ),
            topRated = getTopRatedMoviesUseCase(
                deviceLanguage
            ).cachedIn(
                viewModelScope
            )
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(
            10
        ), MoviesState.default
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<MovieScreenUIState> = moviesState.mapLatest { moviesState ->
        MovieScreenUIState(
            moviesState = moviesState,
            favorites = favoritesMoviesUseCase().cachedIn(
                viewModelScope
            ),
            recentlyBrowsed = getRecentlyBrowsedMoviesUseCase().cachedIn(
                viewModelScope
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        MovieScreenUIState.default
    )
    //endregion Variables
}