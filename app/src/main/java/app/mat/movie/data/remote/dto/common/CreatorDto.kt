package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class CreatorDto(
    override val id: Int,
    @Json(name = "credit_id")
    val creditId: String,
    val name: String,
    val gender: Int?,
    @Json(name = "profile_path")
    override val profilePath: String?
) : MemberDto {
    override val firstLine: String = name
    override val secondLine: String? = null
}
