package app.mat.movie.presentation.screen.browse.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.common.type.MovieType
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class BrowseMoviesScreenUIState(
    val selectedMovieType: MovieType,
    val movies: Flow<PagingData<Presentable>>,
    val favoriteMoviesCount: Int
) {
    companion object {
        fun getDefault(
            selectedMovieType: MovieType
        ): BrowseMoviesScreenUIState {
            return BrowseMoviesScreenUIState(
                selectedMovieType = selectedMovieType,
                movies = emptyFlow(),
                favoriteMoviesCount = 0
            )
        }
    }
}