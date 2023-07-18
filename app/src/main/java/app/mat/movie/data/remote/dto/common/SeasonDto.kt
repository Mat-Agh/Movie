package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class SeasonDto(
    override val id: Int,
    @Json(name = "air_date")
    val airDate: String?,
    val name: String,
    val overview: String,
    @Json(name = "episode_count")
    val episodeCount: Int,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "poster_path")
    override val posterPath: String?
) : Presentable {
    override val title: String = name
}
