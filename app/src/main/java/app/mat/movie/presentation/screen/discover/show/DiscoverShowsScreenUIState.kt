package app.mat.movie.presentation.screen.discover.show

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DateRange
import app.mat.movie.data.remote.dto.common.GenreDto
import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import app.mat.movie.data.remote.dto.common.VoteRange
import app.mat.movie.data.remote.dto.show.ShowDto
import app.mat.movie.presentation.screen.discover.movie.SortInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class DiscoverShowsScreenUiState(
    val sortInfo: SortInfo,
    val filterState: ShowFilterState,
    val tvShow: Flow<PagingData<ShowDto>>
) {
    companion object {
        val default: DiscoverShowsScreenUiState = DiscoverShowsScreenUiState(
            sortInfo = SortInfo.default,
            filterState = ShowFilterState.default,
            tvShow = emptyFlow()
        )
    }
}

@Stable
data class ShowFilterState(
    val selectedGenres: List<GenreDto>,
    val availableGenres: List<GenreDto> = emptyList(),
    val selectedWatchProviders: List<ProviderSourceDto>,
    val availableWatchProviders: List<ProviderSourceDto>,
    val showOnlyWithPoster: Boolean,
    val showOnlyWithScore: Boolean,
    val showOnlyWithOverview: Boolean,
    val voteRange: VoteRange = VoteRange(),
    val airDateRange: DateRange = DateRange()
) {
    companion object {
        val default: ShowFilterState = ShowFilterState(
            selectedGenres = emptyList(),
            availableGenres = emptyList(),
            selectedWatchProviders = emptyList(),
            availableWatchProviders = emptyList(),
            showOnlyWithPoster = false,
            showOnlyWithScore = false,
            showOnlyWithOverview = false,
            voteRange = VoteRange(),
            airDateRange = DateRange()
        )
    }

    fun clear(): ShowFilterState = copy(
        selectedGenres = emptyList(),
        selectedWatchProviders = emptyList(),
        showOnlyWithPoster = false,
        showOnlyWithScore = false,
        showOnlyWithOverview = false,
        voteRange = VoteRange(),
        airDateRange = DateRange()
    )
}