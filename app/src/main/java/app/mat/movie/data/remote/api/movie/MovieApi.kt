package app.mat.movie.data.remote.api.movie

import androidx.annotation.FloatRange
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
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("configuration")
    fun getConfig(): Call<ConfigDto>

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String,
        @Query("sort_by") type: SortTypeParam,
        @Query("with_genres") genres: GenresParam,
        @Query("with_watch_providers") watchProviders: WatchProvidersParam,
        @Query("watch_region") watchRegion: String = region,
        @FloatRange(from = 0.0)
        @Query("vote_average.gte")
        voteAverageMin: Float,
        @FloatRange(from = 0.0)
        @Query("vote_average.lte")
        voteAverageMax: Float,
        @Query("release_date.gte")
        fromReleaseDate: DateParam?,
        @Query("release_date.lte")
        toReleaseDate: DateParam?
    ): MoviesResponseDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): MoviesResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): MoviesResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): MoviesResponseDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): MoviesResponseDto

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") standardCode: String
    ): Call<MovieDetailsDto>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") standardCode: String
    ): Call<CreditsDto>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): MoviesResponseDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMoviesRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): MoviesResponseDto

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): MoviesResponseDto

    @GET("movie/{movie_id}/images")
    fun getMovieImages(
        @Path("movie_id") movieId: Int
    ): Call<ImagesResponseDto>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): ReviewsResponseDto

    @GET("movie/{movie_id}/reviews")
    fun getMovieReview(
        @Path("movie_id") movieId: Int
    ): Call<ReviewsResponseDto>

    @GET("genre/movie/list")
    fun getMovieGenres(
        @Query("language") standardCode: String
    ): Call<GenresResponseDto>

    @GET("movie/{movie_id}/watch/providers")
    fun getMovieWatchProviders(
        @Path("movie_id") movieId: Int
    ): Call<WatchProvidersResponseDto>

    @GET("movie/{movie_id}/external_ids")
    fun getMovieExternalIds(
        @Path("movie_id") movieId: Int,
    ): Call<ExternalIdsDto>

    @GET("watch/providers/movie")
    fun getAllMoviesWatchProviders(
        @Query("language") standardCode: String,
        @Query("watch_region") region: String
    ): Call<AllWatchProvidersResponseDto>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") standardCode: String
    ): Call<VideosResponseDto>

    @GET("discover/movie")
    suspend fun getOtherMoviesOfDirector(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String,
        @Query("with_crew") directorId: Int
    ): MoviesResponseDto
}