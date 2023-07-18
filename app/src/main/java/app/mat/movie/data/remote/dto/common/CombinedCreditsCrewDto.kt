package app.mat.movie.data.remote.dto.common

import app.mat.movie.data.remote.type.MediaType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class CombinedCreditsCrewDto(
    override val id: Int,
    val department: String,
    val job: String,
    override val title: String,
    @Json(name = "media_type")
    override val mediaType: MediaType,
    @Json(name = "poster_path")
    override val posterPath: String?
) : CreditPresentable {
    override val infoText: String = job
}
