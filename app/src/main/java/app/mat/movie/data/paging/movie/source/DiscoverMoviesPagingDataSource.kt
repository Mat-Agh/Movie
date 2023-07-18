package app.mat.movie.data.paging.movie.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.data.remote.api.movie.MovieApiHelper
import app.mat.movie.data.remote.dto.common.DateParam
import app.mat.movie.data.remote.dto.common.DateRange
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.GenresParam
import app.mat.movie.data.remote.dto.common.WatchProvidersParam
import app.mat.movie.data.remote.dto.movie.MovieDto
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

class DiscoverMoviesPagingDataSource(
    private val movieApiHelper: MovieApiHelper,
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
    releaseDateRange: DateRange
) : PagingSource<Int, MovieDto>() {

    private val fromReleaseDate = releaseDateRange.from?.let(
        ::DateParam
    )
    private val toReleaseDate = releaseDateRange.to?.let(
        ::DateParam
    )
    private val sortTypeParam = sortType.toSortTypeParam(
        sortOrder
    )

    override fun getRefreshKey(
        state: PagingState<Int, MovieDto>
    ): Int? = state.anchorPosition?.let { anchorPosition ->
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

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieDto> = try {
        val nextPage = params.key ?: 1
        val movieResponse = movieApiHelper.discoverMovies(
            page = nextPage,
            standardCode = deviceLanguage.languageCode,
            region = deviceLanguage.region,
            sortTypeParam = sortTypeParam,
            genresParam = genresParam,
            watchProvidersParam = watchProvidersParam,
            voteRange = voteRange,
            fromReleaseDate = fromReleaseDate,
            toReleaseDate = toReleaseDate
        )
        val currentPage = movieResponse.page
        val totalPages = movieResponse.totalPages

        LoadResult.Page(
            data = movieResponse.movies
                .filter { movie ->
                    if (onlyWithPosters) !movie.posterPath.isNullOrEmpty()
                    else true
                }
                .filter { movie ->
                    if (onlyWithScore) movie.voteCount > 0 && movie.voteAverage > 0f
                    else true
                }.filter { movie ->
                    if (onlyWithOverview) movie.overView.isNotBlank()
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