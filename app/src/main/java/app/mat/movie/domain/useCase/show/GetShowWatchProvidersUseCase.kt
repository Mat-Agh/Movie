package app.mat.movie.domain.useCase.show

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.WatchProvidersDto

interface GetShowWatchProvidersUseCase {
    suspend operator fun invoke(
        tvShowId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<WatchProvidersDto?>
}