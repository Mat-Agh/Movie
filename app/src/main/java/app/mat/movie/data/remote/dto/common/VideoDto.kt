package app.mat.movie.data.remote.dto.common

import app.mat.movie.data.remote.type.VideoSiteDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(
    generateAdapter = true
)
data class VideoDto(
    val id: String,
    @Json(name = "iso_639_1")
    val language: String,
    @Json(name = "iso_3166_1")
    val region: String,
    val key: String,
    val site: VideoSiteDto,
    val size: Int,
    val type: String,
    val official: Boolean,
    @Json(name = "published_at")
    val publishedAt: Date?
) {
    fun getThumbnailUrl(): String {
        return when (site) {
            VideoSiteDto.YouTube -> {
                "https://img.youtube.com/vi/${key}/hqdefault.jpg"
            }

            VideoSiteDto.Vimeo -> {
                "https://vumbnail.com/${key}.jpg"
            }
        }
    }
}

