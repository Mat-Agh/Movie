package app.mat.movie.domain.userCase.common

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto

interface GetPersonExternalIdsUseCase {
    suspend operator fun invoke(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<ExternalIdsDto>
}