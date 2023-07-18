package app.mat.movie.presentation.screen.details.person

import androidx.compose.runtime.Stable
import app.mat.movie.data.remote.dto.common.CombinedCreditsDto
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import app.mat.movie.data.remote.type.ExternalIdsResource

@Stable
data class PersonDetailsScreenUIState(
    val startRoute: String?,
    val details: PersonDetailsDto?,
    val externalIds: List<ExternalIdsResource>?,
    val credits: CombinedCreditsDto?,
    val error: String?
) {
    companion object {
        fun getDefault(): PersonDetailsScreenUIState = PersonDetailsScreenUIState(
            startRoute = null,
            details = null,
            externalIds = null,
            credits = null,
            error = null
        )
    }
}

@Stable
sealed class PersonDetailsState {
    object Loading : PersonDetailsState()
    object Error : PersonDetailsState()
    data class Result(
        val details: PersonDetailsDto
    ) : PersonDetailsState()
}