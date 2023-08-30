package app.mat.movie.domain.useCase.show

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.show.ShowDto
import app.mat.movie.presentation.screen.discover.movie.SortInfo
import app.mat.movie.presentation.screen.discover.show.ShowFilterState
import kotlinx.coroutines.flow.Flow

interface GetDiscoverShowsUseCase {
    operator fun invoke(
        sortInfo: SortInfo,
        filterState: ShowFilterState,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowDto>>
}