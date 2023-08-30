package app.mat.movie.domain.useCase.movie

import androidx.paging.PagingData
import app.mat.movie.common.type.MovieType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow

interface GetMoviesOfTypeUseCase {
    operator fun invoke(
        type: MovieType,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<Presentable>>
}