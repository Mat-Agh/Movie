package app.mat.movie.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.data.local.database.Database
import app.mat.movie.data.local.entity.show.ShowDetailsEntity
import app.mat.movie.data.local.entity.show.ShowEntity
import app.mat.movie.data.local.type.ShowTypeEntity
import app.mat.movie.data.paging.other.ReviewsPagingDataSource
import app.mat.movie.data.paging.show.mediator.ShowDetailsPagingRemoteMediator
import app.mat.movie.data.paging.show.mediator.ShowRemotePagingMediator
import app.mat.movie.data.paging.show.source.DiscoverTvShowsPagingDataSource
import app.mat.movie.data.paging.show.source.ShowDetailsResponsePagingDataSource
import app.mat.movie.data.remote.api.show.ShowApiHelper
import app.mat.movie.data.remote.dto.common.DateRange
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.remote.dto.common.GenresParam
import app.mat.movie.data.remote.dto.common.ImagesResponseDto
import app.mat.movie.data.remote.dto.common.ReviewDto
import app.mat.movie.data.remote.dto.common.ReviewsResponseDto
import app.mat.movie.data.remote.dto.common.VideosResponseDto
import app.mat.movie.data.remote.dto.common.WatchProvidersParam
import app.mat.movie.data.remote.dto.common.WatchProvidersResponseDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.remote.dto.show.ShowDto
import app.mat.movie.data.repository.ShowRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalPagingApi::class)
class TvShowRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val showApiHelper: ShowApiHelper,
    private val database: Database
) : ShowRepository {
    override fun discoverShows(
        deviceLanguage: DeviceLanguageDto,
        sortType: SortType,
        sortOrder: SortOrder,
        genresParam: GenresParam,
        watchProvidersParam: WatchProvidersParam,
        voteRange: ClosedFloatingPointRange<Float>,
        onlyWithPosters: Boolean,
        onlyWithScore: Boolean,
        onlyWithOverview: Boolean,
        airDateRange: DateRange
    ): Flow<PagingData<ShowDto>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        DiscoverTvShowsPagingDataSource(
            showApiHelper = showApiHelper,
            deviceLanguage = deviceLanguage,
            sortType = sortType,
            sortOrder = sortOrder,
            genresParam = genresParam,
            watchProvidersParam = watchProvidersParam,
            voteRange = voteRange,
            onlyWithPosters = onlyWithPosters,
            onlyWithScore = onlyWithScore,
            onlyWithOverview = onlyWithOverview,
            airDateRange = airDateRange
        )
    }.flow.flowOn(
        defaultDispatcher
    )

    override fun topRatedShows(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        remoteMediator = ShowRemotePagingMediator(
            deviceLanguage = deviceLanguage,
            showApiHelper = showApiHelper,
            database = database,
            type = ShowTypeEntity.TopRated
        ),
        pagingSourceFactory = {
            database.showsDao().getAllTvShows(
                type = ShowTypeEntity.TopRated,
                language = deviceLanguage.languageCode
            )
        }
    ).flow.flowOn(
        defaultDispatcher
    )

    override fun onTheAirShows(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowDetailsEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        remoteMediator = ShowDetailsPagingRemoteMediator(
            deviceLanguage = deviceLanguage,
            showApiHelper = showApiHelper,
            database = database
        ),
        pagingSourceFactory = {
            database.showsDetailsDao().getAllTvShow(
                language = deviceLanguage.languageCode
            )
        }
    ).flow.flowOn(
        defaultDispatcher
    )

    override fun trendingShows(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        remoteMediator = ShowRemotePagingMediator(
            deviceLanguage = deviceLanguage,
            showApiHelper = showApiHelper,
            database = database,
            type = ShowTypeEntity.Trending
        ),
        pagingSourceFactory = {
            database.showsDao().getAllTvShows(
                type = ShowTypeEntity.Trending,
                language = deviceLanguage.languageCode
            )
        }
    ).flow.flowOn(
        defaultDispatcher
    )

    override fun popularShows(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        remoteMediator = ShowRemotePagingMediator(
            deviceLanguage = deviceLanguage,
            showApiHelper = showApiHelper,
            database = database,
            type = ShowTypeEntity.Popular
        ),
        pagingSourceFactory = {
            database.showsDao().getAllTvShows(
                type = ShowTypeEntity.Popular,
                language = deviceLanguage.languageCode
            )
        }
    ).flow.flowOn(defaultDispatcher)

    override fun airingTodayShows(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        remoteMediator = ShowRemotePagingMediator(
            deviceLanguage = deviceLanguage,
            showApiHelper = showApiHelper,
            database = database,
            type = ShowTypeEntity.AiringToday
        ),
        pagingSourceFactory = {
            database.showsDao().getAllTvShows(
                type = ShowTypeEntity.AiringToday,
                language = deviceLanguage.languageCode
            )
        }
    ).flow.flowOn(
        defaultDispatcher
    )

    override fun similarShows(
        showId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowDto>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        ShowDetailsResponsePagingDataSource(
            showId = showId,
            deviceLanguage = deviceLanguage,
            apiHelperMethod = showApiHelper::getSimilarShows
        )
    }.flow.flowOn(defaultDispatcher)

    override fun showsRecommendations(
        showId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowDto>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        ShowDetailsResponsePagingDataSource(
            showId = showId,
            deviceLanguage = deviceLanguage,
            apiHelperMethod = showApiHelper::getShowsRecommendations
        )
    }.flow.flowOn(
        defaultDispatcher
    )

    override fun getShowDetails(
        showId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Call<ShowDetailsDto> = showApiHelper.getTvShowDetails(
        showId,
        deviceLanguage.languageCode
    )

    override fun showImages(
        showId: Int
    ): Call<ImagesResponseDto> = showApiHelper.getTvShowImages(
        showId
    )


    override fun showReviews(
        showId: Int
    ): Flow<PagingData<ReviewDto>> = Pager(
        PagingConfig(
            pageSize = 5
        )
    ) {
        ReviewsPagingDataSource(
            mediaId = showId,
            apiHelperMethod = showApiHelper::getTvShowReviews
        )
    }.flow.flowOn(
        defaultDispatcher
    )

    override fun showReview(
        showId: Int
    ): Call<ReviewsResponseDto> = showApiHelper.getTvShowReview(showId)

    override fun watchProviders(
        showId: Int
    ): Call<WatchProvidersResponseDto> = showApiHelper.getTvShowWatchProviders(showId)

    override fun getExternalIds(
        showId: Int
    ): Call<ExternalIdsDto> = showApiHelper.getTvShowExternalIds(
        showId
    )

    override fun showVideos(
        showId: Int,
        standardCode: String
    ): Call<VideosResponseDto> = showApiHelper.getTvShowVideos(
        showId,
        standardCode
    )
}