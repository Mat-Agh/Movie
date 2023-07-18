package app.mat.movie.presentation.screen.related.movies

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.common.type.RelationType
import app.mat.movie.data.remote.dto.movie.MovieDto
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class RelatedMoviesScreenUiState(
    val relationType: RelationType,
    val movies: Flow<PagingData<MovieDto>>,
    val startRoute: String
) {
    companion object {
        fun getDefault(relationType: RelationType): RelatedMoviesScreenUiState {
            return RelatedMoviesScreenUiState(
                relationType = relationType,
                movies = emptyFlow(),
                startRoute = NavigationBarGraphScreen.MovieScreen.route
            )
        }
    }
}