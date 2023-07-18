package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class CastDto(
    val adult: Boolean,
    val gender: Int?,
    override val id: Int,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    val name: String,
    val order: Int,
    @Json(name = "original_name")
    val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path")
    override val profilePath: String?,
    val roles: List<RoleDto>,
    @Json(name = "total_episode_count")
    val totalEpisodeCount: Int
) : MemberDto {
    override val firstLine: String = name
    override val secondLine: String = roles.joinToString(separator = ", ") { role ->
        role.character
    }
}
