package app.mat.movie.domain.userCase.common

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.PersonDetailsDto

interface GetPersonDetailsUseCase {
    suspend operator fun invoke(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<PersonDetailsDto>
}