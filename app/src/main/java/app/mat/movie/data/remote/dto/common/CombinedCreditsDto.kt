package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class CombinedCreditsDto(
    val cast: List<CombinedCreditsCastDto>,
    val crew: List<CombinedCreditsCrewDto>
)
