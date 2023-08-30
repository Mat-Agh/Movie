package app.mat.movie.domain.useCase.common

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import app.mat.movie.data.repository.PersonRepository
import javax.inject.Inject

class GetPersonDetailsUseCaseImpl @Inject constructor(
    private val personRepository: PersonRepository
) : GetPersonDetailsUseCase {
    override suspend operator fun invoke(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<PersonDetailsDto> = personRepository.getPersonDetails(
        personId = personId,
        deviceLanguage = deviceLanguage
    ).awaitApiResponse()
}