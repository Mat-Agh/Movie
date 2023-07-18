package app.mat.movie.presentation.screen.discover.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.show.GetAllShowsWatchProvidersUseCase
import app.mat.movie.domain.userCase.show.GetDiscoverShowsUseCase
import app.mat.movie.domain.userCase.show.GetShowGenresUseCase
import app.mat.movie.presentation.screen.discover.movie.SortInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class DiscoverShowsViewModel @Inject constructor(
    getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    getTvShowGenresUseCase: GetShowGenresUseCase,
    getAllTvShowWatchProvidersUseCase: GetAllShowsWatchProvidersUseCase,
    private val getDiscoverTvShowUseCase: GetDiscoverShowsUseCase
) : ViewModel() {
    //region Variables
    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()
    private val availableTvSeriesGenres = getTvShowGenresUseCase()
    private val availableWatchProviders = getAllTvShowWatchProvidersUseCase()
    private val sortInfo: MutableStateFlow<SortInfo> = MutableStateFlow(
        SortInfo.default
    )
    private val _filterState: MutableStateFlow<ShowFilterState> = MutableStateFlow(
        ShowFilterState.default
    )
    private val filterState: StateFlow<ShowFilterState> = combine(
        _filterState,
        availableTvSeriesGenres,
        availableWatchProviders
    ) { filterState, genres, watchProviders ->
        filterState.copy(
            availableGenres = genres,
            availableWatchProviders = watchProviders
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ShowFilterState.default
    )

    val uiState: StateFlow<DiscoverShowsScreenUiState> = combine(
        deviceLanguage,
        sortInfo,
        filterState
    ) { deviceLanguage, sortInfo, filterState ->
        val tvShow = getDiscoverTvShowUseCase(
            sortInfo = sortInfo,
            filterState = filterState,
            deviceLanguage = deviceLanguage
        ).cachedIn(
            viewModelScope
        )

        DiscoverShowsScreenUiState(
            sortInfo = sortInfo,
            filterState = filterState,
            tvShow = tvShow
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        DiscoverShowsScreenUiState.default
    )
    //endregion Variables

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
        state: ShowFilterState
    ) {
        viewModelScope.launch {
            _filterState.emit(
                state
            )
        }
    }
    //endregion Public Methods
}