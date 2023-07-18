package app.mat.movie.domain.userCase.show

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.SeasonDetailsDto
import app.mat.movie.data.repository.SeasonRepository
import javax.inject.Inject

class GetSeasonDetailsUseCaseImpl @Inject constructor(
    private val seasonRepository: SeasonRepository
) : GetSeasonDetailsUseCase {
    override suspend operator fun invoke(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<SeasonDetailsDto> = seasonRepository.seasonDetails(
        tvShowId = tvShowId,
        seasonNumber = seasonNumber,
        deviceLanguage = deviceLanguage
    ).awaitApiResponse()
}