package app.mat.movie.presentation.screen.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.data.local.entity.movie.MovieFavoriteEntity
import app.mat.movie.data.local.entity.movie.RecentlyBrowsedMovieEntity
import app.mat.movie.data.remote.dto.common.DetailPresentable
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MovieScreenUIState(
    val moviesState: MoviesState,
    val favorites: Flow<PagingData<MovieFavoriteEntity>>,
    val recentlyBrowsed: Flow<PagingData<RecentlyBrowsedMovieEntity>>
) {
    companion object {
        val default: MovieScreenUIState = MovieScreenUIState(
            moviesState = MoviesState.default,
            favorites = emptyFlow(),
            recentlyBrowsed = emptyFlow()
        )
    }
}

@Stable
data class MoviesState(
    val discover: Flow<PagingData<Presentable>>,
    val upcoming: Flow<PagingData<Presentable>>,
    val topRated: Flow<PagingData<Presentable>>,
    val trending: Flow<PagingData<Presentable>>,
    val nowPlaying: Flow<PagingData<DetailPresentable>>
) {
    companion object {
        val default: MoviesState = MoviesState(
            discover = emptyFlow(),
            upcoming = emptyFlow(),
            topRated = emptyFlow(),
            trending = emptyFlow(),
            nowPlaying = emptyFlow()
        )
    }
}

