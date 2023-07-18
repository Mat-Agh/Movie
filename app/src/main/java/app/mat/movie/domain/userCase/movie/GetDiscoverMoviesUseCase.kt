package app.mat.movie.domain.userCase.movie

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieDto
import app.mat.movie.presentation.screen.discover.movie.MovieFilterState
import app.mat.movie.presentation.screen.discover.movie.SortInfo
import kotlinx.coroutines.flow.Flow

interface GetDiscoverMoviesUseCase {
    operator fun invoke(
        sortInfo: SortInfo,
        filterState: MovieFilterState,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDto>>
}