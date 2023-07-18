package app.mat.movie.data.remote.dto.show

import app.mat.movie.data.remote.dto.common.DetailPresentable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class ShowDto(
    override val id: Int,
    @Json(name = "poster_path")
    override val posterPath: String?,
    @Json(name = "overview")
    override val overView: String,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "origin_country")
    val originCountry: List<String>?,
    val name: String?,
    @Json(name = "backdrop_path")
    override val backdropPath: String?,
    val popularity: Float?,
    @Json(name = "vote_count")
    override val voteCount: Int,
    @Json(name = "vote_average")
    override val voteAverage: Float
) : DetailPresentable {
    override val adult: Boolean? = null
    override val title: String = name.orEmpty()
}
