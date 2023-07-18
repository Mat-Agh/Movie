package app.mat.movie.data.paging.movie.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import app.mat.movie.data.local.database.Database
import app.mat.movie.data.local.entity.movie.MovieEntity
import app.mat.movie.data.local.entity.movie.MoviesRemoteKeysEntity
import app.mat.movie.data.local.type.MovieTypeEntity
import app.mat.movie.data.remote.api.movie.MovieApiHelper
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MoviesResponseDto
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(
    ExperimentalPagingApi::class
)
class MoviesRemotePagingMediator(
    private val deviceLanguage: DeviceLanguageDto,
    private val type: MovieTypeEntity,
    private val movieApiHelper: MovieApiHelper,
    private val database: Database
) : RemoteMediator<Int, MovieEntity>() {
    private val movieCacheDao = database.moviesDao()
    private val moviesRemoteKeysDao = database.moviesRemoteKeysDao()

    private val apiMovieHelperMethod: suspend (Int, String, String) -> MoviesResponseDto =
        when (type) {
            MovieTypeEntity.TopRated -> movieApiHelper::getTopRatedMovies
            MovieTypeEntity.Discover -> movieApiHelper::discoverMovies
            MovieTypeEntity.Trending -> movieApiHelper::getTrendingMovies
            MovieTypeEntity.Upcoming -> movieApiHelper::getUpcomingMovies
            MovieTypeEntity.Popular -> movieApiHelper::getPopularMovies
        }

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            moviesRemoteKeysDao.getRemoteKey(
                type = type,
                language = deviceLanguage.languageCode
            )
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(
            1,
            TimeUnit.MILLISECONDS
        )

        return if ((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
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
                        moviesRemoteKeysDao.getRemoteKey(
                            type = type,
                            language = deviceLanguage.languageCode
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

            val result = apiMovieHelperMethod(
                page,
                deviceLanguage.languageCode,
                deviceLanguage.region
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieCacheDao.deleteMoviesOfType(
                        type = type,
                        language = deviceLanguage.languageCode
                    )
                    moviesRemoteKeysDao.deleteRemoteKeysOfType(
                        type = type,
                        language = deviceLanguage.languageCode
                    )
                }

                val nextPage = if (result.movies.isNotEmpty()) {
                    page + 1
                } else null

                val movieEntities = result.movies.map { movie ->
                    MovieEntity(
                        id = movie.id,
                        type = type,
                        title = movie.title,
                        originalTitle = movie.originalTitle,
                        posterPath = movie.posterPath,
                        language = deviceLanguage.languageCode
                    )
                }

                moviesRemoteKeysDao.insertKey(
                    MoviesRemoteKeysEntity(
                        language = deviceLanguage.languageCode,
                        type = type,
                        nextPage = nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                )
                movieCacheDao.addMovies(
                    movieEntities
                )
            }
            MediatorResult.Success(
                endOfPaginationReached = result.movies.isEmpty()
            )
        } catch (exception: IOException) {
            MediatorResult.Error(
                exception
            )
        } catch (exception: HttpException) {
            MediatorResult.Error(
                exception
            )
        } catch (exception: JsonDataException) {
            MediatorResult.Error(
                exception
            )
        }
    }
}