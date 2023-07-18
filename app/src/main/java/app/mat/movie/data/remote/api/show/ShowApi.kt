package app.mat.movie.data.remote.api.show

import androidx.annotation.FloatRange
import app.mat.movie.data.remote.dto.common.AggregatedCreditsDto
import app.mat.movie.data.remote.dto.common.AllWatchProvidersResponseDto
import app.mat.movie.data.remote.dto.common.ConfigDto
import app.mat.movie.data.remote.dto.common.DateParam
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.remote.dto.common.GenresParam
import app.mat.movie.data.remote.dto.common.GenresResponseDto
import app.mat.movie.data.remote.dto.common.ImagesResponseDto
import app.mat.movie.data.remote.dto.common.ReviewsResponseDto
import app.mat.movie.data.remote.dto.common.SeasonDetailsDto
import app.mat.movie.data.remote.dto.common.VideosResponseDto
import app.mat.movie.data.remote.dto.common.WatchProvidersParam
import app.mat.movie.data.remote.dto.common.WatchProvidersResponseDto
import app.mat.movie.data.remote.dto.show.SeasonsResponseDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.remote.dto.show.ShowsResponseDto
import app.mat.movie.data.remote.type.SortTypeParam
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowApi {
    @GET("configuration")
    fun getConfig(): Call<ConfigDto>

    @GET("discover/tv")
    suspend fun discoverShows(
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
        @Query("first_air_date.gte")
        fromAirDate: DateParam?,
        @Query("first_air_date.lte")
        toAirDate: DateParam?
    ): ShowsResponseDto

    @GET("tv/top_rated")
    suspend fun getTopRatedShows(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): ShowsResponseDto

    @GET("tv/on_the_air")
    suspend fun getOnTheAirShows(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): ShowsResponseDto

    @GET("tv/popular")
    suspend fun getPopularShows(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): ShowsResponseDto

    @GET("tv/airing_today")
    suspend fun getAiringTodayShows(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): ShowsResponseDto

    @GET("tv/{tv_id}")
    fun getTvShowDetails(
        @Path("tv_id") tvShowId: Int,
        @Query("language") standardCode: String
    ): Call<ShowDetailsDto>

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarShows(
        @Path("tv_id") tvShowId: Int,
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): ShowsResponseDto

    @GET("tv/{tv_id}/recommendations")
    suspend fun getShowsRecommendations(
        @Path("tv_id") tvShowId: Int,
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): ShowsResponseDto

    @GET("tv/{tv_id}/season/{season_number}")
    fun getTvSeasons(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") standardCode: String
    ): Call<SeasonsResponseDto>

    @GET("trending/tv/week")
    suspend fun getTrendingShows(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String
    ): ShowsResponseDto

    @GET("tv/{tv_id}/season/{season_number}")
    fun getSeasonDetails(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") standardCode: String
    ): Call<SeasonDetailsDto>

    @GET("tv/{tv_id}/season/{season_number}/aggregate_credits")
    fun getSeasonCredits(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") standardCode: String,
    ): Call<AggregatedCreditsDto>

    @GET("tv/{tv_id}/images")
    fun getTvShowImages(
        @Path("tv_id") tvShowId: Int
    ): Call<ImagesResponseDto>

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/images")
    fun getEpisodeImages(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): Call<ImagesResponseDto>

    @GET("tv/{tv_id}/reviews")
    suspend fun getTvShowReviews(
        @Path("tv_id") movieId: Int,
        @Query("page") page: Int
    ): ReviewsResponseDto

    @GET("tv/{tv_id}/reviews")
    fun getTvShowReview(
        @Path("tv_id") tvShowId: Int
    ): Call<ReviewsResponseDto>

    @GET("genre/tv/list")
    fun getShowsGenres(
        @Query("language") standardCode: String
    ): Call<GenresResponseDto>

    @GET("tv/{tv_id}/watch/providers")
    fun getTvShowWatchProviders(
        @Path("tv_id") tvShowId: Int
    ): Call<WatchProvidersResponseDto>

    @GET("tv/{tv_id}/external_ids")
    fun getTvShowExternalIds(
        @Path("tv_id") tvShowId: Int,
    ): Call<ExternalIdsDto>

    @GET("watch/providers/tv")
    fun getAllShowsWatchProviders(
        @Query("language") standardCode: String,
        @Query("watch_region") region: String
    ): Call<AllWatchProvidersResponseDto>

    @GET("tv/{tv_id}/videos")
    fun getTvShowVideos(
        @Path("tv_id") tvShowId: Int,
        @Query("language") standardCode: String
    ): Call<VideosResponseDto>

    @GET("tv/{tv_id}/season/{season_number}/videos")
    fun getSeasonVideos(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") standardCode: String
    ): Call<VideosResponseDto>
}