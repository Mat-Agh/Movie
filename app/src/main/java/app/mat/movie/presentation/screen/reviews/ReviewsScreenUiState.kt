package app.mat.movie.presentation.screen.reviews

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.ReviewDto
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class ReviewsScreenUiState(
    val startRoute: String,
    val reviews: Flow<PagingData<ReviewDto>>
) {
    companion object {
        val default: ReviewsScreenUiState = ReviewsScreenUiState(
            startRoute = NavigationBarGraphScreen.MovieScreen.route,
            reviews = emptyFlow()
        )
    }
}