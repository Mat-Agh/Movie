package app.mat.movie.data.paging.other

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.mat.movie.data.remote.dto.common.ReviewDto
import app.mat.movie.data.remote.dto.common.ReviewsResponseDto
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

class ReviewsPagingDataSource(
    private val mediaId: Int,
    private inline val apiHelperMethod: suspend (
        Int,
        Int
    ) -> ReviewsResponseDto
) : PagingSource<Int, ReviewDto>() {
    override fun getRefreshKey(
        state: PagingState<Int, ReviewDto>
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
    ): LoadResult<Int, ReviewDto> {
        return try {
            val nextPage = params.key ?: 1
            val reviewsResponse = apiHelperMethod(
                mediaId,
                nextPage
            )
            val currentPage = reviewsResponse.page
            val totalPages = reviewsResponse.totalPages

            LoadResult.Page(
                data = reviewsResponse.results,
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