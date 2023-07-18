package app.mat.movie.domain.userCase.movie

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow

interface GetTrendingMoviesUseCase {
    operator fun invoke(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<Presentable>>
}