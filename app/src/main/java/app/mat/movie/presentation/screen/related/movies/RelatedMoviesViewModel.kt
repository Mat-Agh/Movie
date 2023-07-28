package app.mat.movie.presentation.screen.related.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.type.RelationType
import app.mat.movie.common.util.toJsonObject
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.movie.GetRelatedMoviesOfTypeUseCase
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.RelatedMoviesScreenData
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(
    ExperimentalCoroutinesApi::class
)
@HiltViewModel
class RelatedMoviesViewModel @Inject constructor(
    getDeviceLanguageUseCaseImpl: GetDeviceLanguageUseCase,
    private val getRelatedMoviesOfTypeUseCase: GetRelatedMoviesOfTypeUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    //region Variables
    private val movieId: Int = savedStateHandle[RelatedMoviesScreenData.RELATED_MOVIES_REQUIRED_ARGUMENT_MOVIE_ID] ?: 0

    private val relationTypeString: String? = savedStateHandle[RelatedMoviesScreenData.RELATED_MOVIES_REQUIRED_ARGUMENT_RELATION_TYPE]
    private val relationType: RelationType = relationTypeString?.toJsonObject(
        RelationType::class.java
    ) ?: RelationType.Recommended
    private val startRoute: String = savedStateHandle[RelatedMoviesScreenData.RELATED_MOVIES_REQUIRED_ARGUMENT_START_ROUTE] ?: NavigationBarGraphScreen.MovieScreen.route
    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCaseImpl()

    val uiState: StateFlow<RelatedMoviesScreenUiState> =
        deviceLanguage.mapLatest { deviceLanguage ->
            val movies = getRelatedMoviesOfTypeUseCase(
                movieId = movieId,
                type = relationType,
                deviceLanguage = deviceLanguage
            ).cachedIn(
                viewModelScope
            )

            RelatedMoviesScreenUiState(
                relationType = relationType,
                movies = movies,
                startRoute = startRoute
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            RelatedMoviesScreenUiState.getDefault(
                relationType = relationType
            )
        )
    //endregion
}