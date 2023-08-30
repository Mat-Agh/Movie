package app.mat.movie.domain.useCase.common

import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDeviceLanguageUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) : GetDeviceLanguageUseCase {
    override operator fun invoke(): Flow<DeviceLanguageDto> = configRepository.getDeviceLanguage()
}