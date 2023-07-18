package app.mat.movie.data.remote.type

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = false
)
enum class MediaType(
    val value: String
) {
    @Json(name = "movie")
    Movie("movie"),

    @Json(name = "tv")
    Tv("tv"),

    @Json(name = "person")
    Person("person")
}