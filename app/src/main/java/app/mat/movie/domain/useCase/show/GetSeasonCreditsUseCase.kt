package app.mat.movie.domain.useCase.show

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.AggregatedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto

interface GetSeasonCreditsUseCase {
    suspend operator fun invoke(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<AggregatedCreditsDto>
}