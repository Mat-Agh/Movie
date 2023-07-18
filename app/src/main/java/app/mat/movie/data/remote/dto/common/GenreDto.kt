package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class GenreDto(
    val id: Int,
    val name: String
)
