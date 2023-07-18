package app.mat.movie.data.paging.show.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.show.ShowDto
import app.mat.movie.data.remote.dto.show.ShowsResponseDto
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

class ShowDetailsResponsePagingDataSource(
    private val showId: Int,
    private val deviceLanguage: DeviceLanguageDto,
    private inline val apiHelperMethod: suspend (
        Int,
        Int,
        String,
        String
    ) -> ShowsResponseDto
) : PagingSource<Int, ShowDto>() {
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
            val tvShowResponse = apiHelperMethod(
                showId,
                nextPage,
                deviceLanguage.languageCode,
                deviceLanguage.region
            )

            val currentPage = tvShowResponse.page
            val totalPages = tvShowResponse.totalPages

            LoadResult.Page(
                data = tvShowResponse.tvShows,
                prevKey = if (nextPage == 1) null
                else nextPage - 1,
                nextKey = if (currentPage + 1 > totalPages) null
                else currentPage + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: JsonDataException) {
            LoadResult.Error(e)
        }
    }
}