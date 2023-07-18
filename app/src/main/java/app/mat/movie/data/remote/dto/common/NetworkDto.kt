package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class NetworkDto(
    val id: Int,
    val name: String,
    @Json(name = "logo_path")
    val logoPath: String?,
    @Json(name = "origin_country")
    val originCountry: String
)
