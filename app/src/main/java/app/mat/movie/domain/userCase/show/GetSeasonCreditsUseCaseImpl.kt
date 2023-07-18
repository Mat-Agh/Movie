package app.mat.movie.domain.userCase.show

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.AggregatedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.repository.SeasonRepository
import javax.inject.Inject

class GetSeasonCreditsUseCaseImpl @Inject constructor(
    private val seasonRepository: SeasonRepository
) : GetSeasonCreditsUseCase {
    override suspend operator fun invoke(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<AggregatedCreditsDto> = seasonRepository.seasonCredits(
        tvShowId = tvShowId,
        seasonNumber = seasonNumber,
        isoCode = deviceLanguage.languageCode
    ).awaitApiResponse()
}