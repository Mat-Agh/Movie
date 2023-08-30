package app.mat.movie.domain.useCase.common

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.CombinedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.repository.PersonRepository
import javax.inject.Inject

class GetCombinedCreditsUseCaseImpl @Inject constructor(
    private val personRepository: PersonRepository
) : GetCombinedCreditsUseCase {
    override suspend operator fun invoke(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<CombinedCreditsDto> = personRepository.getCombinedCredits(
        personId = personId,
        deviceLanguage = deviceLanguage
    ).awaitApiResponse()
}