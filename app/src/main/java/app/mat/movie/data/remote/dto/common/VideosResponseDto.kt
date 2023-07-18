package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideosResponseDto(
    val id: Int,
    val results: List<VideoDto>
)