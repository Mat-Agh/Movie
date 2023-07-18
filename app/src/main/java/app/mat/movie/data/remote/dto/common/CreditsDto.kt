package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class CreditsDto(
    val cast: List<CastMemberDto>?,
    val crew: List<CrewMemberDto>?
)
