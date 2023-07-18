package app.mat.movie.data.remote.api.movie

import app.mat.movie.data.remote.dto.common.AllWatchProvidersResponseDto
import app.mat.movie.data.remote.dto.common.ConfigDto
import app.mat.movie.data.remote.dto.common.CreditsDto
import app.mat.movie.data.remote.dto.common.DateParam
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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieApiHelperImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieApiHelper {
    override fun getConfig(): Call<ConfigDto> = movieApi.getConfig()

    override suspend fun discoverMovies(
        page: Int,
        standardCode: String,
        region: String,
        sortTypeParam: SortTypeParam,
        genresParam: GenresParam,
        watchProvidersParam: WatchProvidersParam,
        voteRange: ClosedFloatingPointRange<Float>,
        fromReleaseDate: DateParam?,
        toReleaseDate: DateParam?
    ): MoviesResponseDto = movieApi.discoverMovies(
        page,
        standardCode,
        region,
        sortTypeParam,
        genresParam,
        watchProvidersParam,
        voteAverageMin = voteRange.start,
        voteAverageMax = voteRange.endInclusive,
        fromReleaseDate = fromReleaseDate,
        toReleaseDate = toReleaseDate
    )

    override suspend fun getPopularMovies(
        page: Int,
        standardCode: String,
        region: String
    ): MoviesResponseDto = movieApi.getPopularMovies(
        page,
        standardCode,
        region
    )

    override suspend fun getUpcomingMovies(
        page: Int,
        standardCode: String,
        region: String
    ): MoviesResponseDto = movieApi.getUpcomingMovies(
        page,
        standardCode,
        region
    )

    override suspend fun getTopRatedMovies(
        page: Int,
        standardCode: String,
        region: String
    ): MoviesResponseDto = movieApi.getTopRatedMovies(
        page,
        standardCode,
        region
    )

    override suspend fun getNowPlayingMovies(
        page: Int,
        standardCode: String,
        region: String
    ): MoviesResponseDto = movieApi.getNowPlayingMovies(
        page,
        standardCode,
        region
    )

    override fun getMovieDetails(
        movieId: Int,
        standardCode: String
    ): Call<MovieDetailsDto> = movieApi.getMovieDetails(
        movieId,
        standardCode
    )

    override fun getMovieCredits(
        movieId: Int,
        standardCode: String
    ): Call<CreditsDto> = movieApi.getMovieCredits(
        movieId,
        standardCode
    )

    override suspend fun getSimilarMovies(
        movieId: Int,
        page: Int,
        standardCode: String,
        region: String
    ): MoviesResponseDto = movieApi.getSimilarMovies(
        movieId,
        page,
        standardCode,
        region
    )

    override suspend fun getMoviesRecommendations(
        movieId: Int,
        page: Int,
        standardCode: String,
        region: String
    ): MoviesResponseDto = movieApi.getMoviesRecommendations(
        movieId,
        page,
        standardCode,
        region
    )

    override suspend fun getTrendingMovies(
        page: Int,
        standardCode: String,
        region: String
    ): MoviesResponseDto = movieApi.getTrendingMovies(
        page,
        standardCode,
        region
    )

    override fun getMovieImages(
        movieId: Int
    ): Call<ImagesResponseDto> = movieApi.getMovieImages(
        movieId
    )

    override suspend fun getMovieReviews(
        movieId: Int,
        page: Int
    ): ReviewsResponseDto = movieApi.getMovieReviews(
        movieId,
        page
    )

    override fun getMovieReview(
        movieId: Int
    ): Call<ReviewsResponseDto> = movieApi.getMovieReview(
        movieId
    )

    override fun getMoviesGenres(
        standardCode: String
    ): Call<GenresResponseDto> = movieApi.getMovieGenres(
        standardCode
    )

    override fun getMovieWatchProviders(
        movieId: Int
    ): Call<WatchProvidersResponseDto> = movieApi.getMovieWatchProviders(
        movieId
    )

    override fun getMovieExternalIds(
        movieId: Int
    ): Call<ExternalIdsDto> = movieApi.getMovieExternalIds(
        movieId
    )

    override fun getAllMoviesWatchProviders(
        standardCode: String,
        region: String
    ): Call<AllWatchProvidersResponseDto> = movieApi.getAllMoviesWatchProviders(
        standardCode,
        region
    )

    override fun getMovieVideos(
        movieId: Int,
        standardCode: String
    ): Call<VideosResponseDto> = movieApi.getMovieVideos(
        movieId,
        standardCode
    )

    override suspend fun getOtherMoviesOfDirector(
        page: Int,
        standardCode: String,
        region: String,
        directorId: Int
    ): MoviesResponseDto = movieApi.getOtherMoviesOfDirector(
        page,
        standardCode,
        region,
        directorId
    )
}