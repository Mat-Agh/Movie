package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class SeasonDetailsDto(
    @Json(name = "air_date")
    val airDate: Date?,
    val episodes: List<EpisodeDto>,
    val name: String,
    override val overView: String,
    override val id: Int,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "poster_path")
    override val posterPath: String?
) : DetailPresentable {
    override val adult: Boolean? = null
    override val backdropPath: String? = null
    override val voteAverage: Float = 0f
    override val voteCount: Int = 0
    override val title: String = name
}
