package app.mat.movie.domain.useCase.movie

import androidx.paging.PagingData
import app.mat.movie.common.type.RelationType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieDto
import kotlinx.coroutines.flow.Flow

interface GetRelatedMoviesOfTypeUseCase {
    operator fun invoke(
        movieId: Int,
        type: RelationType,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDto>>
}