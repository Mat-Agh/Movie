package app.mat.movie.domain.userCase.show

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.GenresParam
import app.mat.movie.data.remote.dto.common.WatchProvidersParam
import app.mat.movie.data.remote.dto.show.ShowDto
import app.mat.movie.data.repository.ShowRepository
import app.mat.movie.presentation.screen.discover.movie.SortInfo
import app.mat.movie.presentation.screen.discover.show.ShowFilterState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDiscoverShowsUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetDiscoverShowsUseCase {
    override operator fun invoke(
        sortInfo: SortInfo,
        filterState: ShowFilterState,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowDto>> {
        return tvShowRepository.discoverShows(
            deviceLanguage = deviceLanguage,
            sortType = sortInfo.sortType,
            sortOrder = sortInfo.sortOrder,
            genresParam = GenresParam(
                filterState.selectedGenres
            ),
            watchProvidersParam = WatchProvidersParam(
                filterState.selectedWatchProviders
            ),
            voteRange = filterState.voteRange.current,
            onlyWithPosters = filterState.showOnlyWithPoster,
            onlyWithScore = filterState.showOnlyWithScore,
            onlyWithOverview = filterState.showOnlyWithOverview,
            airDateRange = filterState.airDateRange
        )
    }
}