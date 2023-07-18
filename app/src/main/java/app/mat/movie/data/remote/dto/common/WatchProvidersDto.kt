package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class WatchProvidersDto(
    val link: String,

    val rent: List<ProviderSourceDto>?,

    val buy: List<ProviderSourceDto>?,

    @Json(name = "flatrate")
    val flatRate: List<ProviderSourceDto>?
)