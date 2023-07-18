package app.mat.movie.presentation.screen.seasons

import androidx.compose.runtime.Stable
import app.mat.movie.data.remote.dto.common.AggregatedCreditsDto
import app.mat.movie.data.remote.dto.common.ImageDto
import app.mat.movie.data.remote.dto.common.SeasonDetailsDto
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen

@Stable
data class SeasonDetailsScreenUiState(
    val startRoute: String,
    val seasonDetails: SeasonDetailsDto?,
    val aggregatedCredits: AggregatedCreditsDto?,
    val videos: List<VideoDto>?,
    val episodeCount: Int?,
    val episodeStills: Map<Int, List<ImageDto>>,
    val error: String?
) {
    companion object {
        val default: SeasonDetailsScreenUiState = SeasonDetailsScreenUiState(
            startRoute = NavigationBarGraphScreen.ShowScreen.route,
            seasonDetails = null,
            aggregatedCredits = null,
            videos = null,
            episodeCount = null,
            episodeStills = emptyMap(),
            error = null
        )
    }
}