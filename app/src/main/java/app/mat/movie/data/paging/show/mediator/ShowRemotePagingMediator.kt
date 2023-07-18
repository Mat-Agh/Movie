package app.mat.movie.data.paging.show.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import app.mat.movie.data.local.database.Database
import app.mat.movie.data.local.entity.show.ShowEntity
import app.mat.movie.data.local.entity.show.ShowsRemoteKeysEntity
import app.mat.movie.data.local.type.ShowTypeEntity
import app.mat.movie.data.remote.api.show.ShowApiHelper
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.show.ShowsResponseDto
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class ShowRemotePagingMediator(
    private val deviceLanguage: DeviceLanguageDto,
    private val type: ShowTypeEntity,
    private val showApiHelper: ShowApiHelper,
    private val database: Database
) : RemoteMediator<Int, ShowEntity>() {
    private val tvShowsDao = database.showsDao()
    private val showRemoteKeysDao = database.showsRemoteKeysDao()

    private val apiHelperMethod: suspend (
        Int,
        String,
        String
    ) -> ShowsResponseDto = when (type) {
        ShowTypeEntity.AiringToday -> showApiHelper::getAiringTodayShows
        ShowTypeEntity.TopRated -> showApiHelper::getTopRatedShows
        ShowTypeEntity.Trending -> showApiHelper::getTrendingShows
        ShowTypeEntity.Popular -> showApiHelper::getPopularShows
        ShowTypeEntity.Discover -> showApiHelper::discoverShows
    }

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            showRemoteKeysDao.getRemoteKey(
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
        state: PagingState<Int, ShowEntity>
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
                        showRemoteKeysDao.getRemoteKey(
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

            val result = apiHelperMethod(
                page,
                deviceLanguage.languageCode,
                deviceLanguage.region
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    tvShowsDao.deleteTvShowsOfType(
                        type = type,
                        language = deviceLanguage.languageCode
                    )
                    showRemoteKeysDao.deleteRemoteKeysOfType(
                        type = type,
                        language = deviceLanguage.languageCode
                    )
                }

                val nextPage = if (result.tvShows.isNotEmpty()) {
                    page + 1
                } else null

                val tvShowEntities = result.tvShows.map { tvSeries ->
                    ShowEntity(
                        id = tvSeries.id,
                        type = type,
                        title = tvSeries.title,
                        originalName = tvSeries.originalName,
                        posterPath = tvSeries.posterPath,
                        language = deviceLanguage.languageCode
                    )
                }

                showRemoteKeysDao.insertKey(
                    ShowsRemoteKeysEntity(
                        language = deviceLanguage.languageCode,
                        type = type,
                        nextPage = nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                )
                tvShowsDao.addTvShow(
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