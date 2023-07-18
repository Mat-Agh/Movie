package app.mat.movie.data.remote.dto.movie

import app.mat.movie.data.remote.dto.common.DetailPresentable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class MovieDto(
    override val id: Int,
    @Json(name = "poster_path")
    override val posterPath: String?,
    override val adult: Boolean?,
    @Json(name = "overview")
    override val overView: String,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    override val title: String,
    @Json(name = "backdrop_path")
    override val backdropPath: String?,
    val popularity: Float?,
    @Json(name = "vote_count")
    override val voteCount: Int,
    val video: Boolean,
    @Json(name = "vote_average")
    override val voteAverage: Float
) : DetailPresentable
