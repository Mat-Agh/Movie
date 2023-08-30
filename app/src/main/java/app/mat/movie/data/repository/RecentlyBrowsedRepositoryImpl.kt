package app.mat.movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.mat.movie.data.local.database.dao.movie.RecentlyBrowsedMoviesDao
import app.mat.movie.data.local.database.dao.show.RecentlyBrowsedTvShowsDao
import app.mat.movie.data.local.entity.movie.RecentlyBrowsedMovieEntity
import app.mat.movie.data.local.entity.show.RecentlyBrowsedShowEntity
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentlyBrowsedRepositoryImpl @Inject constructor(
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val recentlyBrowsedMoviesDao: RecentlyBrowsedMoviesDao,
    private val recentlyBrowsedTvShowsDao: RecentlyBrowsedTvShowsDao
) : RecentlyBrowsedRepository {
    private companion object {
        const val maxItems = 100
    }

    override fun addRecentlyBrowsedMovie(
        movieDetails: MovieDetailsDto
    ) {
        externalScope.launch(
            defaultDispatcher
        ) {
            val recentlyBrowsedMovie = movieDetails.run {
                RecentlyBrowsedMovieEntity(
                    id = id,
                    posterPath = posterPath,
                    title = title,
                    addedDate = Date()
                )
            }

            recentlyBrowsedMoviesDao.deleteAndAdd(
                recentlyBrowsedMovie,
                maxItems = maxItems
            )
        }
    }

    override fun clearRecentlyBrowsedMovies() {
        externalScope.launch(
            defaultDispatcher
        ) {
            recentlyBrowsedMoviesDao.clear()
        }
    }

    override fun clearRecentlyBrowsedTvShows() {
        externalScope.launch(
            defaultDispatcher
        ) {
            recentlyBrowsedTvShowsDao.clear()
        }
    }

    override fun recentlyBrowsedMovies(): Flow<PagingData<RecentlyBrowsedMovieEntity>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        recentlyBrowsedMoviesDao.recentBrowsedMovie().asPagingSourceFactory()()
    }.flow.flowOn(defaultDispatcher)

    override fun addRecentlyBrowsedShows(
        showDetails: ShowDetailsDto
    ) {
        externalScope.launch(
            defaultDispatcher
        ) {
            val recentlyBrowsedTvShow = showDetails.run {
                RecentlyBrowsedShowEntity(
                    id = id,
                    posterPath = posterPath,
                    name = title,
                    addedDate = Date()
                )
            }

            recentlyBrowsedTvShowsDao.deleteAndAdd(
                recentlyBrowsedTvShow,
                maxItems = maxItems
            )
        }
    }

    override fun recentlyBrowsedShows(): Flow<PagingData<RecentlyBrowsedShowEntity>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        recentlyBrowsedTvShowsDao.recentBrowsedTvShow().asPagingSourceFactory()()
    }.flow.flowOn(
        defaultDispatcher
    )
}