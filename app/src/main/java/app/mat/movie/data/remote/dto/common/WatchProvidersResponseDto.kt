package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class WatchProvidersResponseDto(
    val id: Int,
    val results: Map<String, WatchProvidersDto>
)