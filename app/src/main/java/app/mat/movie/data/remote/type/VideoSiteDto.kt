package app.mat.movie.data.remote.type

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = false
)
enum class VideoSiteDto(
    val value: String
) {
    @Json(name = "YouTube")
    YouTube("YouTube"),

    @Json(name = "Vimeo")
    Vimeo("Vimeo")
}