package app.mat.movie.domain.useCase.show

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow

interface GetTrendingShowsUseCase {
    operator fun invoke(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<Presentable>>
}