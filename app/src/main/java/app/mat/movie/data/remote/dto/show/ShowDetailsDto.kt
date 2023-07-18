package app.mat.movie.data.remote.dto.show

import app.mat.movie.data.remote.dto.common.CreatorDto
import app.mat.movie.data.remote.dto.common.DetailPresentable
import app.mat.movie.data.remote.dto.common.EpisodeDto
import app.mat.movie.data.remote.dto.common.GenreDto
import app.mat.movie.data.remote.dto.common.NetworkDto
import app.mat.movie.data.remote.dto.common.ProductionCompanyDto
import app.mat.movie.data.remote.dto.common.ProductionCountryDto
import app.mat.movie.data.remote.dto.common.SeasonDto
import app.mat.movie.data.remote.dto.common.SpokenLanguageDto
import app.mat.movie.data.remote.type.TvShowStatusTypeDto
import app.mat.movie.data.remote.type.TvTypeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(
    generateAdapter = true
)
data class ShowDetailsDto(
    override val id: Int,

    @Json(name = "backdrop_path")
    override val backdropPath: String?,

    @Json(name = "poster_path")
    override val posterPath: String?,

    @Json(name = "created_by")
    val creators: List<CreatorDto>,

    val homepage: String,

    val genres: List<GenreDto>,

    @Json(name = "in_production")
    val inProduction: Boolean,

    val languages: List<String>,

    @Json(name = "first_air_date")
    val firstAirDate: Date?,

    @Json(name = "last_air_date")
    val lastAirDate: Date?,

    @Json(name = "last_episode_to_air")
    val lastEpisodeToAir: EpisodeDto?,

    val name: String,

    @Json(name = "next_episode_to_air")
    val nextEpisodeToAir: EpisodeDto?,

    val networks: List<NetworkDto>,

    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Int?,

    @Json(name = "number_of_seasons")
    val numberOfSeasons: Int,

    @Json(name = "origin_country")
    val originCountry: List<String>?,

    @Json(name = "original_language")
    val originalLanguage: String,

    @Json(name = "original_name")
    val originalName: String?,

    override val overView: String,

    val popularity: Float,

    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompanyDto>,

    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountryDto>,

    val seasons: List<SeasonDto>,

    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto>,

    val status: TvShowStatusTypeDto,

    val tagline: String,

    val type: TvTypeDto,

    @Json(name = "vote_average")
    override val voteAverage: Float,

    @Json(name = "vote_count")
    override val voteCount: Int
) : DetailPresentable {
    override val adult: Boolean? = null
    override val title: String = name
}
