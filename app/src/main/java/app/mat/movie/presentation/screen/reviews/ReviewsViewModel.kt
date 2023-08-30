package app.mat.movie.presentation.screen.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.util.toJsonObject
import app.mat.movie.data.remote.type.MediaType
import app.mat.movie.domain.useCase.common.GetMediaTypeReviewsUseCase
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    getMediaTypeReviewsUseCase: GetMediaTypeReviewsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    //region Variables
    private val mediaId: Int = savedStateHandle[Companion.ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_MEDIA_ID] ?: 0
    private val mediaTypeString: String? = savedStateHandle[Companion.ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_MEDIA_TYPE]
    private val mediaType: MediaType = mediaTypeString?.toJsonObject(
        MediaType::class.java
    ) ?: MediaType.Movie
    private val startRoute: String = savedStateHandle[Companion.ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_START_ROUTE] ?: NavigationBarGraphScreen.MovieScreen.route

    val uiState: StateFlow<ReviewsScreenUiState> = MutableStateFlow(
        ReviewsScreenUiState(
            startRoute = startRoute,
            reviews = getMediaTypeReviewsUseCase(
                mediaId = mediaId,
                mediaType = mediaType
            ).cachedIn(
                viewModelScope
            )
        )
    ).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ReviewsScreenUiState.default
    )
    //endregion Variables
}