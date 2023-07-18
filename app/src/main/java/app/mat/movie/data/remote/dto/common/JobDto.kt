package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JobDto(
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "episode_count")
    val episodeCount: Int,
    val job: String
)
