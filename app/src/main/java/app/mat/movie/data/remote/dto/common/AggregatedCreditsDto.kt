package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class AggregatedCreditsDto(
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
    val id: Int
)
