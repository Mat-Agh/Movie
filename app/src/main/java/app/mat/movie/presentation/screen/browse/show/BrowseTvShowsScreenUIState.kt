package app.mat.movie.presentation.screen.browse.show

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.common.type.ShowType
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class BrowseTvShowsScreenUIState(
    val selectedTvShowType: ShowType,
    val tvShow: Flow<PagingData<Presentable>>,
    val favoriteTvShowsCount: Int
) {
    companion object {
        fun getDefault(
            selectedTvShowType: ShowType
        ): BrowseTvShowsScreenUIState = BrowseTvShowsScreenUIState(
            selectedTvShowType = selectedTvShowType,
            tvShow = emptyFlow(),
            favoriteTvShowsCount = 0
        )
    }
}
