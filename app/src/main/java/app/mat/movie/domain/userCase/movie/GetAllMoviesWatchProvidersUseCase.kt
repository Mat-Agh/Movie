package app.mat.movie.domain.userCase.movie

import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import kotlinx.coroutines.flow.Flow

interface GetAllMoviesWatchProvidersUseCase {
    operator fun invoke(): Flow<List<ProviderSourceDto>>
}