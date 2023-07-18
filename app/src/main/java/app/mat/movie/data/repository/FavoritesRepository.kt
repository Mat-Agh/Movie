package app.mat.movie.data.repository

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.movie.MovieFavoriteEntity
import app.mat.movie.data.local.entity.show.ShowFavoriteEntity
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun likeMovie(
        movieDetails: MovieDetailsDto
    )

    fun likeTvShow(
        showDetails: ShowDetailsDto
    )

    fun unlikeMovie(
        movieDetails: MovieDetailsDto
    )

    fun unlikeTvShows(
        showDetails: ShowDetailsDto
    )

    fun favoriteMovies(): Flow<PagingData<MovieFavoriteEntity>>

    fun favoriteTvShows(): Flow<PagingData<ShowFavoriteEntity>>

    fun getFavoriteMoviesIds(): Flow<List<Int>>

    fun getFavoriteTvShowsIds(): Flow<List<Int>>

    fun getFavoriteMoviesCount(): Flow<Int>

    fun getFavoriteTvShowsCount(): Flow<Int>
}