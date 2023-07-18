package app.mat.movie.data.remote.api.show

import app.mat.movie.data.remote.dto.common.AggregatedCreditsDto
import app.mat.movie.data.remote.dto.common.AllWatchProvidersResponseDto
import app.mat.movie.data.remote.dto.common.ConfigDto
import app.mat.movie.data.remote.dto.common.DateParam
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
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

interface ShowApiHelper {
    fun getConfig(): Call<ConfigDto>

    suspend fun discoverShows(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region,
        sortTypeParam: SortTypeParam = SortTypeParam.PopularityDesc,
        genresParam: GenresParam = GenresParam(genres = emptyList()),
        watchProvidersParam: WatchProvidersParam = WatchProvidersParam(watchProviders = emptyList()),
        voteRange: ClosedFloatingPointRange<Float> = 0f..10f,
        fromAirDate: DateParam? = null,
        toAirDate: DateParam? = null
    ): ShowsResponseDto

    suspend fun getTopRatedShows(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): ShowsResponseDto

    suspend fun getOnTheAirShows(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): ShowsResponseDto

    suspend fun getPopularShows(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): ShowsResponseDto

    suspend fun getAiringTodayShows(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): ShowsResponseDto

    fun getTvShowDetails(
        tvShowId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<ShowDetailsDto>

    suspend fun getSimilarShows(
        tvShowId: Int,
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): ShowsResponseDto

    suspend fun getShowsRecommendations(
        tvShowId: Int,
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): ShowsResponseDto

    fun getTvSeasons(
        tvShowId: Int,
        seasonNumber: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<SeasonsResponseDto>

    suspend fun getTrendingShows(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): ShowsResponseDto

    fun getSeasonDetails(
        tvShowId: Int,
        seasonNumber: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<SeasonDetailsDto>

    fun getTvShowImages(tvShowId: Int): Call<ImagesResponseDto>

    fun getEpisodeImages(
        tvShowId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): Call<ImagesResponseDto>

    suspend fun getTvShowReviews(tvShowId: Int, page: Int): ReviewsResponseDto

    fun getTvShowReview(tvShowId: Int): Call<ReviewsResponseDto>

    fun getShowsGenres(standardCode: String = DeviceLanguageDto.default.languageCode): Call<GenresResponseDto>

    fun getTvShowWatchProviders(
        tvShowId: Int
    ): Call<WatchProvidersResponseDto>

    fun getTvShowExternalIds(
        tvShowId: Int
    ): Call<ExternalIdsDto>

    fun getAllShowsWatchProviders(
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region
    ): Call<AllWatchProvidersResponseDto>

    fun getTvShowVideos(
        tvShowId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
    ): Call<VideosResponseDto>

    fun getSeasonVideos(
        tvShowId: Int,
        seasonNumber: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
    ): Call<VideosResponseDto>

    fun getSeasonCredits(
        tvShowId: Int,
        seasonNumber: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<AggregatedCreditsDto>
}