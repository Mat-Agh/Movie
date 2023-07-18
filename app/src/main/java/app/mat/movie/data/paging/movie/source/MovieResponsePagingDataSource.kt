package app.mat.movie.data.paging.movie.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieDto
import app.mat.movie.data.remote.dto.movie.MoviesResponseDto
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

class MovieResponsePagingDataSource(
    private val language: String = DeviceLanguageDto.default.languageCode,
    private val region: String = DeviceLanguageDto.default.region,
    private inline val apiMovieHelperMethod: suspend (
        Int,
        String,
        String
    ) -> MoviesResponseDto
) : PagingSource<Int, MovieDto>() {
    override fun getRefreshKey(
        state: PagingState<Int, MovieDto>
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
    ): LoadResult<Int, MovieDto> {
        return try {
            val nextPage = params.key ?: 1
            val movieResponse = apiMovieHelperMethod(
                nextPage,
                language,
                region
            )
            val currentPage = movieResponse.page
            val totalPages = movieResponse.totalPages

            LoadResult.Page(
                data = movieResponse.movies,
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