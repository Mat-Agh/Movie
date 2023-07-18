package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class CollectionResponseDto(
    val id: Int,
    val name: String,
    val overview: String?,
    val parts: List<PartDto>,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Transient
    val posterUrl: String? = null,
    @Transient
    val backdropUrl: String? = null
)