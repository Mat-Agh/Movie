package app.mat.movie.presentation.screen.browse.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.type.MovieType
import app.mat.movie.common.util.toJsonObject
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.movie.ClearRecentlyBrowsedMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetFavoritesMovieCountUseCase
import app.mat.movie.domain.userCase.movie.GetMoviesOfTypeUseCase
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BrowseMoviesViewModel @Inject constructor(
    getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    private val getMoviesOfTypeUseCase: GetMoviesOfTypeUseCase,
    getFavoritesMoviesCountUseCase: GetFavoritesMovieCountUseCase,
    private val getClearRecentlyBrowsedMoviesUseCase: ClearRecentlyBrowsedMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    //region Variables
    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()

    private val movieTypeString: String? = savedStateHandle[Companion.BrowseMovieScreenData.BROWSE_MOVIE_REQUIRED_ARGUMENT_MOVIE_TYPE]
    private val movieType: MovieType? = movieTypeString?.toJsonObject(
        type = MovieType::class.java
    )

    private val favoriteMoviesCount: StateFlow<Int> = getFavoritesMoviesCountUseCase()
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(
                10
            ),
            0
        )

    val uiState: StateFlow<BrowseMoviesScreenUIState> = combine(
        deviceLanguage, favoriteMoviesCount
    ) { deviceLanguage, favoriteMoviesCount ->
        val movies = getMoviesOfTypeUseCase(
            type = movieType ?: MovieType.TopRated,
            deviceLanguage = deviceLanguage
        ).cachedIn(
            viewModelScope
        )

        BrowseMoviesScreenUIState(
            selectedMovieType = movieType ?: MovieType.TopRated,
            movies = movies,
            favoriteMoviesCount = favoriteMoviesCount
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        BrowseMoviesScreenUIState.getDefault(
            movieType ?: MovieType.TopRated
        )
    )
    //endregion Variables

    //region Public Methods
    fun onClearClicked() = getClearRecentlyBrowsedMoviesUseCase()
    //endregion Public Methods
}