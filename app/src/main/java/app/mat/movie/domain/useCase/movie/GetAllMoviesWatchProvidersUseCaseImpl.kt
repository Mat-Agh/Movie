package app.mat.movie.domain.useCase.movie

import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import app.mat.movie.data.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMoviesWatchProvidersUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) : GetAllMoviesWatchProvidersUseCase {
    override operator fun invoke(): Flow<List<ProviderSourceDto>> = configRepository.getAllMoviesWatchProviders()
}