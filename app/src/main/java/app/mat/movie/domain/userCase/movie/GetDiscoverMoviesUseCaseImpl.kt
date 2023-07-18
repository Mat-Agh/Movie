package app.mat.movie.domain.userCase.movie

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.GenresParam
import app.mat.movie.data.remote.dto.common.WatchProvidersParam
import app.mat.movie.data.remote.dto.movie.MovieDto
import app.mat.movie.data.repository.MovieRepository
import app.mat.movie.presentation.screen.discover.movie.MovieFilterState
import app.mat.movie.presentation.screen.discover.movie.SortInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDiscoverMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetDiscoverMoviesUseCase {
    override operator fun invoke(
        sortInfo: SortInfo,
        filterState: MovieFilterState,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDto>> {
        return movieRepository.discoverMovies(
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
            releaseDateRange = filterState.releaseDateRange
        )
    }
}