package app.mat.movie.data.repository

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.movie.RecentlyBrowsedMovieEntity
import app.mat.movie.data.local.entity.show.RecentlyBrowsedShowEntity
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import kotlinx.coroutines.flow.Flow

interface RecentlyBrowsedRepository {
    fun addRecentlyBrowsedMovie(
        movieDetails: MovieDetailsDto
    )

    fun clearRecentlyBrowsedMovies()

    fun clearRecentlyBrowsedTvShows()

    fun recentlyBrowsedMovies(): Flow<PagingData<RecentlyBrowsedMovieEntity>>

    fun addRecentlyBrowsedShows(showDetails: ShowDetailsDto)

    fun recentlyBrowsedShows(): Flow<PagingData<RecentlyBrowsedShowEntity>>
}