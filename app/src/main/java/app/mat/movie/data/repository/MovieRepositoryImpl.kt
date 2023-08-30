package app.mat.movie.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.data.local.database.Database
import app.mat.movie.data.local.entity.movie.MovieDetailEntity
import app.mat.movie.data.local.entity.movie.MovieEntity
import app.mat.movie.data.local.type.MovieTypeEntity
import app.mat.movie.data.paging.movie.mediator.MovieDetailsPagingRemoteMediator
import app.mat.movie.data.paging.movie.mediator.MoviesRemotePagingMediator
import app.mat.movie.data.paging.movie.source.DirectorOtherMoviePagingDataSource
import app.mat.movie.data.paging.movie.source.DiscoverMoviesPagingDataSource
import app.mat.movie.data.paging.movie.source.MovieDetailsResponsePagingDataSource
import app.mat.movie.data.paging.other.ReviewsPagingDataSource
import app.mat.movie.data.remote.api.movie.MovieApiHelper
import app.mat.movie.data.remote.api.other.OtherApiHelper
import app.mat.movie.data.remote.dto.common.CollectionResponseDto
import app.mat.movie.data.remote.dto.common.CreditsDto
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
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.dto.movie.MovieDto
import app.mat.movie.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val movieApiHelper: MovieApiHelper,
    private val database: Database,
    private val otherApiHelper: OtherApiHelper
) : MovieRepository {
    override fun discoverMovies(
        deviceLanguage: DeviceLanguageDto,
        sortType: SortType,
        sortOrder: SortOrder,
        genresParam: GenresParam,
        watchProvidersParam: WatchProvidersParam,
        voteRange: ClosedFloatingPointRange<Float>,
        onlyWithPosters: Boolean,
        onlyWithScore: Boolean,
        onlyWithOverview: Boolean,
        releaseDateRange: DateRange
    ): Flow<PagingData<MovieDto>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        DiscoverMoviesPagingDataSource(
            movieApiHelper = movieApiHelper,
            deviceLanguage = deviceLanguage,
            sortType = sortType,
            sortOrder = sortOrder,
            genresParam = genresParam,
            watchProvidersParam = watchProvidersParam,
            voteRange = voteRange,
            onlyWithPosters = onlyWithPosters,
            onlyWithScore = onlyWithScore,
            onlyWithOverview = onlyWithOverview,
            releaseDateRange = releaseDateRange
        )
    }.flow.flowOn(defaultDispatcher)

    override fun popularMovies(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieEntity>> =
        Pager(
            PagingConfig(
                pageSize = 20
            ),
            remoteMediator = MoviesRemotePagingMediator(
                deviceLanguage = deviceLanguage,
                movieApiHelper = movieApiHelper,
                database = database,
                type = MovieTypeEntity.Popular
            ),
            pagingSourceFactory = {
                database.moviesDao().getAllMovies(
                    type = MovieTypeEntity.Popular,
                    language = deviceLanguage.languageCode
                )
            }
        ).flow.flowOn(
            defaultDispatcher
        )

    override fun upcomingMovies(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieEntity>> =
        Pager(
            PagingConfig(
                pageSize = 20
            ),
            remoteMediator = MoviesRemotePagingMediator(
                deviceLanguage = deviceLanguage,
                movieApiHelper = movieApiHelper,
                database = database,
                type = MovieTypeEntity.Upcoming
            ),
            pagingSourceFactory = {
                database.moviesDao().getAllMovies(
                    type = MovieTypeEntity.Upcoming,
                    language = deviceLanguage.languageCode
                )
            }
        ).flow.flowOn(defaultDispatcher)

    override fun trendingMovies(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieEntity>> =
        Pager(
            PagingConfig(
                pageSize = 20
            ),
            remoteMediator = MoviesRemotePagingMediator(
                movieApiHelper = movieApiHelper,
                deviceLanguage = deviceLanguage,
                database = database,
                type = MovieTypeEntity.Trending
            ),
            pagingSourceFactory = {
                database.moviesDao().getAllMovies(
                    type = MovieTypeEntity.Trending,
                    language = deviceLanguage.languageCode
                )
            }
        ).flow.flowOn(
            defaultDispatcher
        )

    override fun topRatedMovies(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieEntity>> =
        Pager(
            PagingConfig(
                pageSize = 20
            ),
            remoteMediator = MoviesRemotePagingMediator(
                movieApiHelper = movieApiHelper,
                deviceLanguage = deviceLanguage,
                database = database,
                type = MovieTypeEntity.TopRated
            ),
            pagingSourceFactory = {
                database.moviesDao().getAllMovies(
                    type = MovieTypeEntity.TopRated,
                    language = deviceLanguage.languageCode
                )
            }
        ).flow.flowOn(
            defaultDispatcher
        )

    override fun nowPlayingMovies(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDetailEntity>> =
        Pager(
            PagingConfig(
                pageSize = 20
            ),
            remoteMediator = MovieDetailsPagingRemoteMediator(
                movieApiHelper = movieApiHelper,
                deviceLanguage = deviceLanguage,
                database = database,
            ),
            pagingSourceFactory = {
                database.moviesDetailsDao().getALlMovies(
                    language = deviceLanguage.languageCode
                )
            }
        ).flow.flowOn(
            defaultDispatcher
        )

    override fun similarMovies(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDto>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        MovieDetailsResponsePagingDataSource(
            movieId = movieId,
            language = deviceLanguage.languageCode,
            movieApiHelperMethod = movieApiHelper::getSimilarMovies
        )
    }.flow.flowOn(
        defaultDispatcher
    )

    override fun moviesRecommendation(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDto>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        MovieDetailsResponsePagingDataSource(
            movieId = movieId,
            language = deviceLanguage.languageCode,
            movieApiHelperMethod = movieApiHelper::getMoviesRecommendations
        )
    }.flow.flowOn(
        defaultDispatcher
    )

    override fun movieDetails(
        movieId: Int, isoCode: String
    ): Call<MovieDetailsDto> = movieApiHelper.getMovieDetails(
        movieId,
        isoCode
    )

    override fun movieCredits(
        movieId: Int,
        isoCode: String
    ): Call<CreditsDto> = movieApiHelper.getMovieCredits(
        movieId,
        isoCode
    )


    override fun movieImages(
        movieId: Int
    ): Call<ImagesResponseDto> = movieApiHelper.getMovieImages(
        movieId
    )

    override fun movieReviews(
        movieId: Int
    ): Flow<PagingData<ReviewDto>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        ReviewsPagingDataSource(
            mediaId = movieId,
            apiHelperMethod = movieApiHelper::getMovieReviews
        )
    }.flow.flowOn(
        defaultDispatcher
    )

    override fun movieReview(movieId: Int): Call<ReviewsResponseDto> = movieApiHelper.getMovieReview(movieId)

    override fun collection(
        collectionId: Int,
        isoCode: String
    ): Call<CollectionResponseDto> = otherApiHelper.getCollection(collectionId, isoCode)

    override fun watchProviders(
        movieId: Int
    ): Call<WatchProvidersResponseDto> = movieApiHelper.getMovieWatchProviders(movieId)

    override fun getExternalIds(
        movieId: Int
    ): Call<ExternalIdsDto> = movieApiHelper.getMovieExternalIds(
        movieId
    )

    override fun getMovieVideos(
        movieId: Int,
        isoCode: String
    ): Call<VideosResponseDto> = movieApiHelper.getMovieVideos(
        movieId
    )

    override fun moviesOfDirector(
        directorId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDto>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        DirectorOtherMoviePagingDataSource(
            movieApiHelper = movieApiHelper,
            language = deviceLanguage.languageCode,
            region = deviceLanguage.region,
            directorId = directorId
        )
    }.flow.flowOn(
        defaultDispatcher
    )
}