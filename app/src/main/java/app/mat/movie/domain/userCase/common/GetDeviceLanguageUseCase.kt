package app.mat.movie.domain.userCase.common

import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import kotlinx.coroutines.flow.Flow


interface GetDeviceLanguageUseCase {
    operator fun invoke(): Flow<DeviceLanguageDto>
}