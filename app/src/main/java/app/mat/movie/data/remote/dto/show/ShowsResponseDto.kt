package app.mat.movie.data.remote.dto.show

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class ShowsResponseDto(
    val page: Int,
    @Json(name = "results")
    val tvShows: List<ShowDto>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)
