package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class CrewMemberDto(
    override val id: Int,
    val adult: Boolean,
    val gender: Int?,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    val name: String,
    @Json(name = "original_name")
    val originalName: String?,
    val popularity: Float,
    @Json(name = "profile_path")
    override val profilePath: String?,
    @Json(name = "credit_id")
    val creditId: String,
    val department: String,
    val job: String,
) : MemberDto {
    override val firstLine: String = name
    override val secondLine: String = job
}
