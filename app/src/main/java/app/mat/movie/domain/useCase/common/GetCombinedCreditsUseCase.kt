package app.mat.movie.domain.useCase.common

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.CombinedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto

interface GetCombinedCreditsUseCase {
    suspend operator fun invoke(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<CombinedCreditsDto>
}