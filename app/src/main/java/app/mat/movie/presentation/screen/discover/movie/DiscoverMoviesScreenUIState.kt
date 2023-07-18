package app.mat.movie.presentation.screen.discover.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.data.remote.dto.common.DateRange
import app.mat.movie.data.remote.dto.common.GenreDto
import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import app.mat.movie.data.remote.dto.common.VoteRange
import app.mat.movie.data.remote.dto.movie.MovieDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

//State
@Stable
class DiscoverMoviesScreenUIState(
    val sortInfo: SortInfo,
    val filterState: MovieFilterState,
    val movies: Flow<PagingData<MovieDto>>
) {
    companion object {
        val default: DiscoverMoviesScreenUIState = DiscoverMoviesScreenUIState(
            sortInfo = SortInfo.default,
            filterState = MovieFilterState.default,
            movies = emptyFlow()
        )
    }
}

//Event
@Stable
data class SortInfo(
    val sortType: SortType,
    val sortOrder: SortOrder
) {
    companion object {
        val default: SortInfo = SortInfo(
            sortType = SortType.Popularity,
            sortOrder = SortOrder.Desc
        )
    }
}

@Stable
data class MovieFilterState(
    val selectedGenres: List<GenreDto>,
    val availableGenres: List<GenreDto>,
    val selectedWatchProviders: List<ProviderSourceDto>,
    val availableWatchProviders: List<ProviderSourceDto>,
    val showOnlyWithPoster: Boolean,
    val showOnlyWithScore: Boolean,
    val showOnlyWithOverview: Boolean,
    val voteRange: VoteRange,
    val releaseDateRange: DateRange
) {
    companion object {
        val default: MovieFilterState = MovieFilterState(
            selectedGenres = emptyList(),
            availableGenres = emptyList(),
            availableWatchProviders = emptyList(),
            selectedWatchProviders = emptyList(),
            showOnlyWithPoster = false,
            showOnlyWithScore = false,
            showOnlyWithOverview = false,
            voteRange = VoteRange(),
            releaseDateRange = DateRange()
        )
    }

    fun clear(): MovieFilterState = copy(
        selectedGenres = emptyList(),
        selectedWatchProviders = emptyList(),
        showOnlyWithPoster = false,
        showOnlyWithScore = false,
        showOnlyWithOverview = false,
        voteRange = VoteRange(),
        releaseDateRange = DateRange()
    )
}