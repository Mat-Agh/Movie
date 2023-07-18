package app.mat.movie.presentation.screen.related.show

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.common.type.RelationType
import app.mat.movie.data.remote.dto.show.ShowDto
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class RelatedShowScreenUiState(
    val relationType: RelationType,
    val show: Flow<PagingData<ShowDto>>,
    val startRoute: String
) {
    companion object {
        fun getDefault(
            relationType: RelationType
        ): RelatedShowScreenUiState {
            return RelatedShowScreenUiState(
                relationType = relationType,
                show = emptyFlow(),
                startRoute = NavigationBarGraphScreen.MovieScreen.route
            )
        }
    }
}