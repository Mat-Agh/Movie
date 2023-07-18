package app.mat.movie.data.remote.dto.show

import app.mat.movie.data.remote.dto.common.EpisodeDto
import app.mat.movie.data.remote.dto.common.Presentable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class SeasonsResponseDto(
    override val id: Int,
    @Json(name = "air_date")
    val airDate: String?,
    val name: String,
    val overview: String,
    @Json(name = "poster_path")
    override val posterPath: String?,
    @Json(name = "season_number")
    val seasonNumber: Int,
    val episodes: List<EpisodeDto>
) : Presentable {
    override val title: String = name
}
