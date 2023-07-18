package app.mat.movie.domain.userCase.show

import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import kotlinx.coroutines.flow.Flow

interface GetAllShowsWatchProvidersUseCase {
    operator fun invoke(): Flow<List<ProviderSourceDto>>
}