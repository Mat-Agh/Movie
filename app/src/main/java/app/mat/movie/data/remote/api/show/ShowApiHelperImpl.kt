package app.mat.movie.data.remote.api.show

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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowApiHelperImpl @Inject constructor(
    private val showApi: ShowApi
) : ShowApiHelper {
    override fun getConfig(): Call<ConfigDto> = showApi.getConfig()

    override suspend fun discoverShows(
        page: Int,
        standardCode: String,
        region: String,
        sortTypeParam: SortTypeParam,
        genresParam: GenresParam,
        watchProvidersParam: WatchProvidersParam,
        voteRange: ClosedFloatingPointRange<Float>,
        fromAirDate: DateParam?,
        toAirDate: DateParam?
    ): ShowsResponseDto = showApi.discoverShows(
        page,
        standardCode,
        region,
        sortTypeParam,
        genresParam,
        watchProvidersParam,
        voteAverageMin = voteRange.start,
        voteAverageMax = voteRange.endInclusive,
        fromAirDate = fromAirDate,
        toAirDate = toAirDate
    )

    override suspend fun getTopRatedShows(
        page: Int,
        standardCode: String,
        region: String
    ): ShowsResponseDto = showApi.getTopRatedShows(
        page,
        standardCode,
        region
    )

    override suspend fun getOnTheAirShows(
        page: Int,
        standardCode: String,
        region: String
    ): ShowsResponseDto = showApi.getOnTheAirShows(
        page,
        standardCode,
        region
    )

    override suspend fun getPopularShows(
        page: Int,
        standardCode: String,
        region: String
    ): ShowsResponseDto = showApi.getPopularShows(
        page,
        standardCode,
        region
    )

    override suspend fun getAiringTodayShows(
        page: Int,
        standardCode: String,
        region: String
    ): ShowsResponseDto = showApi.getAiringTodayShows(
        page,
        standardCode,
        region
    )

    override fun getTvShowDetails(
        tvShowId: Int,
        standardCode: String
    ): Call<ShowDetailsDto> = showApi.getTvShowDetails(
        tvShowId,
        standardCode
    )

    override suspend fun getSimilarShows(
        tvShowId: Int,
        page: Int,
        standardCode: String,
        region: String
    ): ShowsResponseDto = showApi.getSimilarShows(
        tvShowId,
        page,
        standardCode,
        region
    )

    override suspend fun getShowsRecommendations(
        tvShowId: Int,
        page: Int,
        standardCode: String,
        region: String
    ): ShowsResponseDto = showApi.getShowsRecommendations(
        tvShowId,
        page,
        standardCode,
        region
    )

    override fun getTvSeasons(
        tvShowId: Int,
        seasonNumber: Int,
        standardCode: String
    ): Call<SeasonsResponseDto> = showApi.getTvSeasons(
        tvShowId,
        seasonNumber,
        standardCode
    )

    override suspend fun getTrendingShows(
        page: Int,
        standardCode: String,
        region: String
    ): ShowsResponseDto = showApi.getTrendingShows(
        page,
        standardCode,
        region
    )

    override fun getSeasonDetails(
        tvShowId: Int,
        seasonNumber: Int,
        standardCode: String
    ): Call<SeasonDetailsDto> = showApi.getSeasonDetails(
        tvShowId,
        seasonNumber,
        standardCode
    )

    override fun getTvShowImages(
        tvShowId: Int
    ): Call<ImagesResponseDto> = showApi.getTvShowImages(
        tvShowId
    )

    override fun getEpisodeImages(
        tvShowId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): Call<ImagesResponseDto> = showApi.getEpisodeImages(
        tvShowId,
        seasonNumber,
        episodeNumber
    )

    override suspend fun getTvShowReviews(
        tvShowId: Int,
        page: Int
    ): ReviewsResponseDto = showApi.getTvShowReviews(
        tvShowId,
        page
    )

    override fun getTvShowReview(
        tvShowId: Int
    ): Call<ReviewsResponseDto> = showApi.getTvShowReview(
        tvShowId
    )

    override fun getShowsGenres(
        standardCode: String
    ): Call<GenresResponseDto> = showApi.getShowsGenres(
        standardCode
    )

    override fun getTvShowWatchProviders(
        tvShowId: Int
    ): Call<WatchProvidersResponseDto> = showApi.getTvShowWatchProviders(
        tvShowId
    )

    override fun getTvShowExternalIds(
        tvShowId: Int
    ): Call<ExternalIdsDto> = showApi.getTvShowExternalIds(
        tvShowId
    )

    override fun getAllShowsWatchProviders(
        standardCode: String,
        region: String
    ): Call<AllWatchProvidersResponseDto> = showApi.getAllShowsWatchProviders(
        standardCode,
        region
    )

    override fun getTvShowVideos(
        tvShowId: Int,
        standardCode: String
    ): Call<VideosResponseDto> = showApi.getTvShowVideos(
        tvShowId,
        standardCode
    )

    override fun getSeasonVideos(
        tvShowId: Int,
        seasonNumber: Int,
        standardCode: String
    ): Call<VideosResponseDto> = showApi.getSeasonVideos(
        tvShowId,
        seasonNumber,
        standardCode
    )

    override fun getSeasonCredits(
        tvShowId: Int,
        seasonNumber: Int,
        standardCode: String
    ): Call<AggregatedCreditsDto> = showApi.getSeasonCredits(
        tvShowId,
        seasonNumber,
        standardCode
    )
}