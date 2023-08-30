package app.mat.movie.presentation.screen.browse.show

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.type.ShowType
import app.mat.movie.common.util.toJsonObject
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.domain.useCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.useCase.show.ClearRecentlyBrowsedShowsUseCase
import app.mat.movie.domain.useCase.show.GetFavoriteShowsCountUseCase
import app.mat.movie.domain.useCase.show.GetShowOfTypeUseCase
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BrowseShowsViewModel @Inject constructor(
    getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    getFavoriteShowCountUseCase: GetFavoriteShowsCountUseCase,
    private val getShowOfTypeUseCase: GetShowOfTypeUseCase,
    private val clearRecentlyBrowsedShowUseCase: ClearRecentlyBrowsedShowsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    //region Variables
    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()

    private val showTypeString: String? = savedStateHandle[Companion.BrowseMovieScreenData.BROWSE_MOVIE_REQUIRED_ARGUMENT_MOVIE_TYPE]

    private val showType: ShowType? = showTypeString?.toJsonObject(
        type = ShowType::class.java
    )

    private val favoriteShowCount: StateFlow<Int> = getFavoriteShowCountUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(
            10
        ), 0
    )

    val uiState: StateFlow<BrowseTvShowsScreenUIState> = combine(
        deviceLanguage, favoriteShowCount
    ) { deviceLanguage, favoriteTvShowCount ->
        val tvShow = getShowOfTypeUseCase(
            type = showType ?: ShowType.TopRated,
            deviceLanguage = deviceLanguage
        ).cachedIn(
            viewModelScope
        )

        BrowseTvShowsScreenUIState(
            selectedTvShowType = showType ?: ShowType.TopRated,
            tvShow = tvShow,
            favoriteTvShowsCount = favoriteTvShowCount
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        BrowseTvShowsScreenUIState.getDefault(
            showType ?: ShowType.TopRated
        )
    )
    //endregion

    //region Public Methods
    fun onClearClicked() = clearRecentlyBrowsedShowUseCase()
    //endregion
}