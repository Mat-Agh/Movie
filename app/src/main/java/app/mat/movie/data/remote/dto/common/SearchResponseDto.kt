package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class SearchResponseDto(
    val page: Int,
    @Json(name = "results")
    val results: List<SearchResultDto>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)
