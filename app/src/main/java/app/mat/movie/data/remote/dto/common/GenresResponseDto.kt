package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class GenresResponseDto(
    val genres: List<GenreDto>
)