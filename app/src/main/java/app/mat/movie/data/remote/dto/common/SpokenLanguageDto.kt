package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class SpokenLanguageDto(
    @Json(name = "iso_639_1")
    val iso: String,
    val name: String
)
