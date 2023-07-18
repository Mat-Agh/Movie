package app.mat.movie.domain.userCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.CreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto

interface GetMovieCreditUseCase {
    suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<CreditsDto>
}