package app.mat.movie.data.paging.show.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.data.remote.api.show.ShowApiHelper
import app.mat.movie.data.remote.dto.common.DateParam
import app.mat.movie.data.remote.dto.common.DateRange
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.GenresParam
import app.mat.movie.data.remote.dto.common.WatchProvidersParam
import app.mat.movie.data.remote.dto.show.ShowDto
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

class DiscoverTvShowsPagingDataSource(
    private val showApiHelper: ShowApiHelper,
    private val deviceLanguage: DeviceLanguageDto,
    sortType: SortType = SortType.Popularity,
    sortOrder: SortOrder = SortOrder.Desc,
    private val genresParam: GenresParam = GenresParam(
        genres = emptyList()
    ),
    private val watchProvidersParam: WatchProvidersParam = WatchProvidersParam(
        watchProviders = emptyList()
    ),
    private val voteRange: ClosedFloatingPointRange<Float> = 0f..10f,
    private val onlyWithPosters: Boolean = false,
    private val onlyWithScore: Boolean = false,
    private val onlyWithOverview: Boolean = false,
    airDateRange: DateRange
) : PagingSource<Int, ShowDto>() {
    private val fromAirDate = airDateRange.from?.let(
        ::DateParam
    )
    private val toAirDate = airDateRange.to?.let(
        ::DateParam
    )
    private val sortTypeParam = sortType.toSortTypeParam(
        sortOrder
    )

    override fun getRefreshKey(
        state: PagingState<Int, ShowDto>
    ): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(
                anchorPosition
            )?.prevKey?.plus(
                1
            ) ?: state.closestPageToPosition(
                anchorPosition
            )?.nextKey?.minus(
                1
            )
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ShowDto> {
        return try {
            val nextPage = params.key ?: 1

            val showResponse = showApiHelper.discoverShows(
                page = nextPage,
                standardCode = deviceLanguage.languageCode,
                region = deviceLanguage.region,
                sortTypeParam = sortTypeParam,
                genresParam = genresParam,
                watchProvidersParam = watchProvidersParam,
                voteRange = voteRange,
                fromAirDate = fromAirDate,
                toAirDate = toAirDate
            )

            val currentPage = showResponse.page
            val totalPages = showResponse.totalPages

            LoadResult.Page(
                data = showResponse.tvShows
                    .filter { tvShow ->
                        if (onlyWithPosters) !tvShow.posterPath.isNullOrEmpty()
                        else true
                    }
                    .filter { tvShow ->
                        if (onlyWithScore) tvShow.voteCount > 0 && tvShow.voteAverage > 0f
                        else true
                    }
                    .filter { tvShow ->
                        if (onlyWithOverview) tvShow.overView.isNotBlank()
                        else true
                    },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (currentPage + 1 > totalPages) null
                else currentPage + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(
                exception
            )
        } catch (exception: HttpException) {
            LoadResult.Error(
                exception
            )
        } catch (exception: JsonDataException) {
            LoadResult.Error(
                exception
            )
        }
    }
}