package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class CrewDto(
    val adult: Boolean,
    val department: String,
    val gender: Int?,
    override val id: Int,
    val jobs: List<JobDto>,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    val name: String,
    @Json(name = "original_name")
    val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path")
    override val profilePath: String?,
    @Json(name = "total_episode_count")
    val totalEpisodeCount: Int
) : MemberDto {
    override val firstLine: String = name
    override val secondLine: String = jobs.joinToString(separator = ", ") { job ->
        job.job
    }
}
