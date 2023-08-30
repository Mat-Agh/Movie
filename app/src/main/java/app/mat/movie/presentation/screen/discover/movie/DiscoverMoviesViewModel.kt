package app.mat.movie.presentation.screen.discover.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.domain.useCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.useCase.movie.GetAllMoviesWatchProvidersUseCase
import app.mat.movie.domain.useCase.movie.GetDiscoverMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetMovieGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverMoviesViewModel @Inject constructor(
    getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    getMovieGenresUseCase: GetMovieGenresUseCase,
    getAllMoviesWatchProvidersUseCase: GetAllMoviesWatchProvidersUseCase,
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase
) : ViewModel() {
    //region Variables
    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()
    private val availableMovieGenres = getMovieGenresUseCase()
    private val availableWatchProviders = getAllMoviesWatchProvidersUseCase()
    private val sortInfo: MutableStateFlow<SortInfo> = MutableStateFlow(
        SortInfo.default
    )
    private val _filterState: MutableStateFlow<MovieFilterState> = MutableStateFlow(
        MovieFilterState.default
    )
    private val filterState: StateFlow<MovieFilterState> = combine(
        _filterState,
        availableMovieGenres,
        availableWatchProviders
    ) { filterState, genres, watchProviders ->
        filterState.copy(
            availableGenres = genres,
            availableWatchProviders = watchProviders
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        MovieFilterState.default
    )

    val uiState: StateFlow<DiscoverMoviesScreenUIState> = combine(
        deviceLanguage, sortInfo, filterState
    ) { deviceLanguage, sortInfo, filterState ->
        val movies = getDiscoverMoviesUseCase(
            sortInfo = sortInfo,
            filterState = filterState,
            deviceLanguage = deviceLanguage
        ).cachedIn(
            viewModelScope
        )

        DiscoverMoviesScreenUIState(
            sortInfo = sortInfo,
            filterState = filterState,
            movies = movies
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        DiscoverMoviesScreenUIState.default
    )
    //endregion

    //region Public Methods
    fun onSortTypeChange(
        sortType: SortType
    ) {
        viewModelScope.launch {
            val currentSortInfo = sortInfo.value

            sortInfo.emit(
                currentSortInfo.copy(
                    sortType = sortType
                )
            )
        }
    }

    fun onSortOrderChange(
        sortOrder: SortOrder
    ) {
        viewModelScope.launch {
            val currentSortInfo = sortInfo.value

            sortInfo.emit(
                currentSortInfo.copy(
                    sortOrder = sortOrder
                )
            )
        }
    }

    fun onFilterStateChange(
        state: MovieFilterState
    ) {
        viewModelScope.launch {
            _filterState.emit(
                state
            )
        }
    }
    //endregion Public Methods
}