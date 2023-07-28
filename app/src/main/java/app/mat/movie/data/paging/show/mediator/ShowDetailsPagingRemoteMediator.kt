package app.mat.movie.data.paging.show.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import app.mat.movie.data.local.database.Database
import app.mat.movie.data.local.entity.show.ShowDetailRemoteKeysEntity
import app.mat.movie.data.local.entity.show.ShowDetailsEntity
import app.mat.movie.data.remote.api.show.ShowApiHelper
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(
    ExperimentalPagingApi::class
)
class ShowDetailsPagingRemoteMediator(
    private val deviceLanguage: DeviceLanguageDto,
    private val showApiHelper: ShowApiHelper,
    private val database: Database
) : RemoteMediator<Int, ShowDetailsEntity>() {
    private val showDetailsDao = database.showsDetailsDao()
    private val showDetailsRemoteKeysDao = database.showsDetailsRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            showDetailsRemoteKeysDao.getRemoteKey(
                deviceLanguage.languageCode
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
        state: PagingState<Int, ShowDetailsEntity>
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
                        showDetailsRemoteKeysDao.getRemoteKey(deviceLanguage.languageCode)
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

            val result = showApiHelper.getOnTheAirShows(
                page = page,
                standardCode = deviceLanguage.languageCode,
                region = deviceLanguage.region
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    showDetailsDao.deleteAllTvShows(
                        deviceLanguage.languageCode
                    )
                    showDetailsRemoteKeysDao.deleteKeys(
                        deviceLanguage.languageCode
                    )
                }

                val nextPage = if (result.tvShows.isNotEmpty()) {
                    page + 1
                } else null

                val tvShowEntities = result.tvShows.map { tvShow ->
                    ShowDetailsEntity(
                        id = tvShow.id,
                        title = tvShow.title,
                        originalTitle = tvShow.originalName,
                        posterPath = tvShow.posterPath,
                        backdropPath = tvShow.backdropPath,
                        overView = tvShow.overView,
                        adult = tvShow.adult,
                        voteAverage = tvShow.voteAverage,
                        voteCount = tvShow.voteCount,
                        language = deviceLanguage.languageCode
                    )
                }
                showDetailsRemoteKeysDao.insertKey(
                    ShowDetailRemoteKeysEntity(
                        language = deviceLanguage.languageCode,
                        nextPage = nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                )
                showDetailsDao.addTvShow(
                    tvShowEntities
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = result.tvShows.isEmpty()
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