package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class ConfigDto(
    @Json(name = "images")
    val imagesConfig: ImagesConfigDto,
    @Json(name = "change_keys")
    val changeKeys: List<String>
)
