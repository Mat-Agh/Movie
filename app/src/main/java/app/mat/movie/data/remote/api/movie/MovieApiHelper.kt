package app.mat.movie.data.remote.api.movie

import app.mat.movie.data.remote.dto.common.AllWatchProvidersResponseDto
import app.mat.movie.data.remote.dto.common.ConfigDto
import app.mat.movie.data.remote.dto.common.CreditsDto
import app.mat.movie.data.remote.dto.common.DateParam
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.remote.dto.common.GenresParam
import app.mat.movie.data.remote.dto.common.GenresResponseDto
import app.mat.movie.data.remote.dto.common.ImagesResponseDto
import app.mat.movie.data.remote.dto.common.ReviewsResponseDto
import app.mat.movie.data.remote.dto.common.VideosResponseDto
import app.mat.movie.data.remote.dto.common.WatchProvidersParam
import app.mat.movie.data.remote.dto.common.WatchProvidersResponseDto
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.dto.movie.MoviesResponseDto
import app.mat.movie.data.remote.type.SortTypeParam
import retrofit2.Call

interface MovieApiHelper {
    fun getConfig(): Call<ConfigDto>

    suspend fun discoverMovies(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region,
        sortTypeParam: SortTypeParam = SortTypeParam.PopularityDesc,
        genresParam: GenresParam = GenresParam(genres = emptyList()),
        watchProvidersParam: WatchProvidersParam = WatchProvidersParam(watchProviders = emptyList()),
        voteRange: ClosedFloatingPointRange<Float> = 0f..10f,
        fromReleaseDate: DateParam? = null,
        toReleaseDate: DateParam? = null
    ): MoviesResponseDto

    suspend fun getPopularMovies(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region,
    ): MoviesResponseDto

    suspend fun getUpcomingMovies(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region,
    ): MoviesResponseDto

    suspend fun getTopRatedMovies(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region,
    ): MoviesResponseDto

    suspend fun getNowPlayingMovies(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): MoviesResponseDto

    fun getMovieDetails(
        movieId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<MovieDetailsDto>

    fun getMovieCredits(
        movieId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<CreditsDto>

    suspend fun getSimilarMovies(
        movieId: Int,
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): MoviesResponseDto

    suspend fun getMoviesRecommendations(
        movieId: Int,
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): MoviesResponseDto

    suspend fun getTrendingMovies(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): MoviesResponseDto

    fun getMovieImages(movieId: Int): Call<ImagesResponseDto>

    suspend fun getMovieReviews(movieId: Int, page: Int): ReviewsResponseDto

    fun getMovieReview(movieId: Int): Call<ReviewsResponseDto>

    fun getMoviesGenres(standardCode: String = DeviceLanguageDto.default.languageCode): Call<GenresResponseDto>

    fun getMovieWatchProviders(
        movieId: Int
    ): Call<WatchProvidersResponseDto>

    fun getMovieExternalIds(
        movieId: Int
    ): Call<ExternalIdsDto>

    fun getAllMoviesWatchProviders(
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): Call<AllWatchProvidersResponseDto>

    fun getMovieVideos(
        movieId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
    ): Call<VideosResponseDto>

    suspend fun getOtherMoviesOfDirector(
        page: Int,
        standardCode: String,
        region: String,
        directorId: Int
    ): MoviesResponseDto
}