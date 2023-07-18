package app.mat.movie.domain.userCase.show

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto

interface GetShowDetailsUseCase {
    suspend operator fun invoke(
        tvShowId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<ShowDetailsDto>
}