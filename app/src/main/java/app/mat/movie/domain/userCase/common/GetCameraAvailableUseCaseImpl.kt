package app.mat.movie.domain.userCase.common

import app.mat.movie.data.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCameraAvailableUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) : GetCameraAvailableUseCase {
    override operator fun invoke(): Flow<Boolean> = configRepository.getCameraAvailable()
}