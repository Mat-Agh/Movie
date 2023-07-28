package app.mat.movie.presentation.screen.show

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.data.local.entity.show.RecentlyBrowsedShowEntity
import app.mat.movie.data.local.entity.show.ShowFavoriteEntity
import app.mat.movie.data.remote.dto.common.DetailPresentable
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class TvShowScreenUIState(
    val showsState: TvShowsState,
    val favorites: Flow<PagingData<ShowFavoriteEntity>>,
    val recentlyBrowsed: Flow<PagingData<RecentlyBrowsedShowEntity>>
) {
    companion object {
        val default: TvShowScreenUIState = TvShowScreenUIState(
            showsState = TvShowsState.default,
            favorites = emptyFlow(),
            recentlyBrowsed = emptyFlow()
        )
    }
}

@Stable
data class TvShowsState(
    val onTheAir: Flow<PagingData<DetailPresentable>>,
    val discover: Flow<PagingData<Presentable>>,
    val topRated: Flow<PagingData<Presentable>>,
    val trending: Flow<PagingData<Presentable>>,
    val airingToday: Flow<PagingData<Presentable>>
) {
    companion object {
        val default: TvShowsState = TvShowsState(
            onTheAir = emptyFlow(),
            discover = emptyFlow(),
            topRated = emptyFlow(),
            trending = emptyFlow(),
            airingToday = emptyFlow()
        )
    }
}