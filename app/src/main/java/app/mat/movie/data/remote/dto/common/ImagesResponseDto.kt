package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImagesResponseDto(
    val id: Int,
    val backdrops: List<ImageDto>?,
    val stills: List<ImageDto>?
)