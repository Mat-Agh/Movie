package app.mat.movie.presentation.screen.details.show

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.WatchProvidersDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.remote.dto.show.ShowDto
import app.mat.movie.presentation.screen.details.movie.AssociatedContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class ShowDetailsScreenUIState(
    val startRoute: String,
    val tvShowDetails: ShowDetailsDto?,
    val additionalTvShowDetailsInfo: AdditionalShowDetailsInfo,
    val associatedTvShow: AssociatedTvShow,
    val associatedContent: AssociatedContent,
    val error: String?
) {
    companion object {
        fun getDefault(
            startRoute: String
        ): ShowDetailsScreenUIState = ShowDetailsScreenUIState(
            startRoute = startRoute,
            tvShowDetails = null,
            additionalTvShowDetailsInfo = AdditionalShowDetailsInfo.default,
            associatedTvShow = AssociatedTvShow.default,
            associatedContent = AssociatedContent.default,
            error = null
        )
    }
}

@Stable
data class AssociatedTvShow(
    val similar: Flow<PagingData<ShowDto>>,
    val recommendations: Flow<PagingData<ShowDto>>
) {
    companion object {
        val default: AssociatedTvShow = AssociatedTvShow(
            similar = emptyFlow(),
            recommendations = emptyFlow()
        )
    }
}

@Stable
data class AdditionalShowDetailsInfo(
    val isFavorite: Boolean,
    val nextEpisodeRemainingDays: Long?,
    val watchProviders: WatchProvidersDto?,
    val reviewsCount: Int
) {
    companion object {
        val default: AdditionalShowDetailsInfo = AdditionalShowDetailsInfo(
            isFavorite = false,
            nextEpisodeRemainingDays = null,
            watchProviders = null,
            reviewsCount = 0
        )
    }
}