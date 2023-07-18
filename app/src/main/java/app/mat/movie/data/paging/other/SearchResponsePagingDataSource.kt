package app.mat.movie.data.paging.other

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.mat.movie.data.remote.api.other.OtherApiHelper
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.SearchResultDto
import app.mat.movie.data.remote.type.MediaType
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

class SearchResponsePagingDataSource(
    private val otherApiHelper: OtherApiHelper,
    private val query: String,
    private val includeAdult: Boolean,
    private val year: Int? = null,
    private val releaseYear: Int? = null,
    private val language: String = DeviceLanguageDto.default.languageCode,
    private val region: String = DeviceLanguageDto.default.region
) : PagingSource<Int, SearchResultDto>() {
    override fun getRefreshKey(
        state: PagingState<Int, SearchResultDto>
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
    ): LoadResult<Int, SearchResultDto> {
        return try {
            val nextPage = params.key ?: 1
            val searchResponse = otherApiHelper.multiSearch(
                page = nextPage,
                standardCode = language,
                region = region,
                query = query,
                includeAdult = includeAdult,
                year = year,
                releaseYear = releaseYear
            )
            val currentPage = searchResponse.page
            val totalPages = searchResponse.totalPages

            LoadResult.Page(
                data = searchResponse.results.filter { result ->
                    result.mediaType in setOf(
                        MediaType.Movie,
                        MediaType.Tv
                    )
                },
                prevKey = if (nextPage == 1) null
                else nextPage - 1,
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