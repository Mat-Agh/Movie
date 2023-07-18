package app.mat.movie.data.repository

import androidx.paging.PagingData
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.data.local.entity.movie.MovieDetailEntity
import app.mat.movie.data.local.entity.movie.MovieEntity
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
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface MovieRepository {
    fun discoverMovies(
        deviceLanguage: DeviceLanguageDto,
        sortType: SortType = SortType.Popularity,
        sortOrder: SortOrder = SortOrder.Desc,
        genresParam: GenresParam = GenresParam(
            genres = emptyList()
        ),
        watchProvidersParam: WatchProvidersParam = WatchProvidersParam(
            watchProviders = emptyList()
        ),
        voteRange: ClosedFloatingPointRange<Float> = 0f..10f,
        onlyWithPosters: Boolean = false,
        onlyWithScore: Boolean = false,
        onlyWithOverview: Boolean = false,
        releaseDateRange: DateRange = DateRange()
    ): Flow<PagingData<MovieDto>>

    fun popularMovies(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<MovieEntity>>

    fun upcomingMovies(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<MovieEntity>>

    fun trendingMovies(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<MovieEntity>>

    fun topRatedMovies(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<MovieEntity>>

    fun nowPlayingMovies(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<MovieDetailEntity>>

    fun similarMovies(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<MovieDto>>

    fun moviesRecommendation(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<MovieDto>>

    fun movieDetails(
        movieId: Int,
        isoCode: String = DeviceLanguageDto.default.languageCode
    ): Call<MovieDetailsDto>

    fun movieCredits(
        movieId: Int,
        isoCode: String = DeviceLanguageDto.default.languageCode
    ): Call<CreditsDto>

    fun movieImages(
        movieId: Int
    ): Call<ImagesResponseDto>

    fun movieReviews(
        movieId: Int
    ): Flow<PagingData<ReviewDto>>

    fun movieReview(
        movieId: Int
    ): Call<ReviewsResponseDto>

    fun collection(
        collectionId: Int,
        isoCode: String = DeviceLanguageDto.default.languageCode
    ): Call<CollectionResponseDto>

    fun watchProviders(
        movieId: Int
    ): Call<WatchProvidersResponseDto>

    fun getExternalIds(
        movieId: Int
    ): Call<ExternalIdsDto>

    fun getMovieVideos(
        movieId: Int,
        isoCode: String = DeviceLanguageDto.default.languageCode
    ): Call<VideosResponseDto>

    fun moviesOfDirector(
        directorId: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<MovieDto>>
}