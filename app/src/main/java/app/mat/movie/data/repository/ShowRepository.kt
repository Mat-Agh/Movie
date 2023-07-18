package app.mat.movie.data.repository

import androidx.paging.PagingData
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.data.local.entity.show.ShowDetailsEntity
import app.mat.movie.data.local.entity.show.ShowEntity
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
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface ShowRepository {
    fun discoverShows(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default,
        sortType: SortType = SortType.Popularity,
        sortOrder: SortOrder = SortOrder.Desc,
        genresParam: GenresParam = GenresParam(genres = emptyList()),
        watchProvidersParam: WatchProvidersParam = WatchProvidersParam(watchProviders = emptyList()),
        voteRange: ClosedFloatingPointRange<Float> = 0f..10f,
        onlyWithPosters: Boolean = false,
        onlyWithScore: Boolean = false,
        onlyWithOverview: Boolean = false,
        airDateRange: DateRange = DateRange()
    ): Flow<PagingData<ShowDto>>

    fun topRatedShows(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<ShowEntity>>

    fun onTheAirShows(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<ShowDetailsEntity>>

    fun trendingShows(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<ShowEntity>>

    fun popularShows(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<ShowEntity>>

    fun airingTodayShows(
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<ShowEntity>>

    fun similarShows(
        showId: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<ShowDto>>

    fun showsRecommendations(
        showId: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<ShowDto>>

    fun getShowDetails(
        showId: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Call<ShowDetailsDto>

    fun showImages(
        showId: Int,
    ): Call<ImagesResponseDto>

    fun showReviews(
        showId: Int
    ): Flow<PagingData<ReviewDto>>

    fun showReview(
        showId: Int
    ): Call<ReviewsResponseDto>

    fun watchProviders(
        showId: Int
    ): Call<WatchProvidersResponseDto>

    fun getExternalIds(
        showId: Int
    ): Call<ExternalIdsDto>

    fun showVideos(
        showId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<VideosResponseDto>
}