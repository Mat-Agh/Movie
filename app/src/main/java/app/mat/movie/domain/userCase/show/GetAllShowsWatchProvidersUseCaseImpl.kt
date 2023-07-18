package app.mat.movie.domain.userCase.show

import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import app.mat.movie.data.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllShowsWatchProvidersUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) : GetAllShowsWatchProvidersUseCase {
    override operator fun invoke(): Flow<List<ProviderSourceDto>> = configRepository.getAllTvShowWatchProviders()
}