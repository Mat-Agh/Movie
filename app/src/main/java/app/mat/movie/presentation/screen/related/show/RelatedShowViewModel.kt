package app.mat.movie.presentation.screen.related.show

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.type.RelationType
import app.mat.movie.common.util.toJsonObject
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.domain.useCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.useCase.show.GetRelatedShowsOfTypeUseCase
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.RelatedShowsScreenData
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
class RelatedShowViewModel @Inject constructor(
    getDeviceLanguageUseCaseImpl: GetDeviceLanguageUseCase,
    private val getRelatedTvSeriesOfTypeUseCase: GetRelatedShowsOfTypeUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    //region Variables
    private val showId: Int = savedStateHandle[RelatedShowsScreenData.RELATED_SHOWS_REQUIRED_ARGUMENT_SHOW_ID] ?: 0
    private val relationTypeString: String? = savedStateHandle[RelatedShowsScreenData.RELATED_SHOWS_REQUIRED_ARGUMENT_RELATION_TYPE]
    private val relationType: RelationType = relationTypeString?.toJsonObject(
        RelationType::class.java
    ) ?: RelationType.Recommended
    private val startRoute: String = savedStateHandle[RelatedShowsScreenData.RELATED_SHOWS_REQUIRED_ARGUMENT_START_ROUTE] ?: NavigationBarGraphScreen.ShowScreen.route

    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCaseImpl()

    val uiState: StateFlow<RelatedShowScreenUiState> =
        deviceLanguage.mapLatest { deviceLanguage ->
            val tvShow = getRelatedTvSeriesOfTypeUseCase(
                tvShowId = showId,
                relationType = relationType,
                deviceLanguage = deviceLanguage
            ).cachedIn(
                viewModelScope
            )

            RelatedShowScreenUiState(
                relationType = relationType,
                show = tvShow,
                startRoute = startRoute
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            RelatedShowScreenUiState.getDefault(
                relationType
            )
        )
    //endregion Variables
}