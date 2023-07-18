package app.mat.movie.data.paging.movie.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import app.mat.movie.data.local.database.Database
import app.mat.movie.data.local.entity.movie.MovieDetailEntity
import app.mat.movie.data.local.entity.movie.MovieDetailsRemoteKeyEntity
import app.mat.movie.data.remote.api.movie.MovieApiHelper
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(
    ExperimentalPagingApi::class
)
class MovieDetailsPagingRemoteMediator(
    private val deviceLanguage: DeviceLanguageDto,
    private val movieApiHelper: MovieApiHelper,
    private val database: Database
) : RemoteMediator<Int, MovieDetailEntity>() {
    private val movieDetailsDao = database.moviesDetailsDao()
    private val movieDetailsRemoteKeysDao = database.moviesDetailsRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            movieDetailsRemoteKeysDao.getRemoteKey(
                deviceLanguage.languageCode
            )
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cashTimOut = TimeUnit.HOURS.convert(
            1,
            TimeUnit.MILLISECONDS
        )

        return if ((System.currentTimeMillis() - remoteKey.lastUpdates) >= cashTimOut) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDetailEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        movieDetailsRemoteKeysDao.getRemoteKey(
                            deviceLanguage.languageCode
                        )
                    } ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )

                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.nextPage
                }
            }

            val result = movieApiHelper.getNowPlayingMovies(
                page = page,
                standardCode = deviceLanguage.languageCode,
                region = deviceLanguage.region
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDetailsDao.deleteMovieDetails(
                        deviceLanguage.languageCode
                    )
                    movieDetailsRemoteKeysDao.deleteKeys(
                        deviceLanguage.languageCode
                    )
                }

                val nextPage = if (result.movies.isNotEmpty()) {
                    page + 1
                } else null

                val movieDetailsEntities = result.movies.map { movie ->
                    MovieDetailEntity(
                        id = movie.id,
                        title = movie.title,
                        originalTitle = movie.originalTitle,
                        posterPath = movie.posterPath,
                        backdropPath = movie.backdropPath,
                        overView = movie.overView,
                        adult = movie.adult,
                        voteAverage = movie.voteAverage,
                        voteCount = movie.voteCount,
                        language = deviceLanguage.languageCode
                    )
                }

                movieDetailsRemoteKeysDao.insertKey(
                    MovieDetailsRemoteKeyEntity(
                        language = deviceLanguage.languageCode,
                        nextPage = nextPage,
                        lastUpdates = System.currentTimeMillis()
                    )
                )

                movieDetailsDao.addMovies(
                    movieDetailsEntities
                )

                MediatorResult.Success(
                    endOfPaginationReached = result.movies.isNotEmpty()
                )
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: JsonDataException) {
            MediatorResult.Error(e)
        }
    }
}