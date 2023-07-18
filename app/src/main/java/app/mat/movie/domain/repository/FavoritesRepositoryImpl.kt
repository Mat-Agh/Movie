package app.mat.movie.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.mat.movie.data.local.database.dao.movie.FavoriteMoviesDao
import app.mat.movie.data.local.database.dao.show.FavoriteShowsDao
import app.mat.movie.data.local.entity.movie.MovieFavoriteEntity
import app.mat.movie.data.local.entity.show.ShowFavoriteEntity
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.repository.FavoritesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val favoriteMoviesDao: FavoriteMoviesDao,
    private val favoriteShowsDao: FavoriteShowsDao
) : FavoritesRepository {
    override fun likeMovie(
        movieDetails: MovieDetailsDto
    ) {
        externalScope.launch(
            defaultDispatcher
        ) {
            val favoriteMovie = movieDetails.run {
                MovieFavoriteEntity(
                    id = id,
                    posterPath = posterPath,
                    title = title,
                    originalTitle = originalTitle,
                    addedDate = Date()
                )
            }
            favoriteMoviesDao.likeMovie(
                favoriteMovie
            )
        }
    }

    override fun likeTvShow(
        showDetails: ShowDetailsDto
    ) {
        externalScope.launch(
            defaultDispatcher
        ) {
            val favoriteTvShow = showDetails.run {
                ShowFavoriteEntity(
                    id = id,
                    posterPath = posterPath,
                    name = name,
                    addedDate = Date()
                )
            }
            favoriteShowsDao.likeTvShow(favoriteTvShow)
        }
    }

    override fun unlikeMovie(
        movieDetails: MovieDetailsDto
    ) {
        externalScope.launch {
            favoriteMoviesDao.unlikeMovie(
                movieDetails.id
            )
        }
    }

    override fun unlikeTvShows(
        showDetails: ShowDetailsDto
    ) {
        externalScope.launch {
            favoriteShowsDao.unlikeTvShow(
                showDetails.id
            )
        }
    }

    override fun favoriteMovies(): Flow<PagingData<MovieFavoriteEntity>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        favoriteMoviesDao.getAllFavoriteMovies().asPagingSourceFactory()()
    }.flow.flowOn(
        defaultDispatcher
    )

    override fun favoriteTvShows(): Flow<PagingData<ShowFavoriteEntity>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        favoriteShowsDao.getAllFavoriteTvShows().asPagingSourceFactory()()
    }.flow.flowOn(
        defaultDispatcher
    )

    override fun getFavoriteMoviesIds(): Flow<List<Int>> = favoriteMoviesDao.favoriteMoviesIds().distinctUntilChanged()

    override fun getFavoriteTvShowsIds(): Flow<List<Int>> = favoriteShowsDao.favoriteTvShowIds().distinctUntilChanged()

    override fun getFavoriteMoviesCount(): Flow<Int> = favoriteMoviesDao.favoriteMoviesCount().distinctUntilChanged()

    override fun getFavoriteTvShowsCount(): Flow<Int> = favoriteShowsDao.favoriteTvShowCount().distinctUntilChanged()
}