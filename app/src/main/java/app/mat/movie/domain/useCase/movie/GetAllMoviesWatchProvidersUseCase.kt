package app.mat.movie.domain.useCase.movie

import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import kotlinx.coroutines.flow.Flow

interface GetAllMoviesWatchProvidersUseCase {
    operator fun invoke(): Flow<List<ProviderSourceDto>>
}