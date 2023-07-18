package app.mat.movie.domain.userCase.common

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.repository.PersonRepository
import javax.inject.Inject

class GetPersonExternalIdsUseCaseImpl @Inject constructor(
    private val personRepository: PersonRepository
) : GetPersonExternalIdsUseCase {
    override suspend operator fun invoke(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<ExternalIdsDto> = personRepository.getExternalIds(
        personId = personId,
        deviceLanguage = deviceLanguage
    ).awaitApiResponse()
}