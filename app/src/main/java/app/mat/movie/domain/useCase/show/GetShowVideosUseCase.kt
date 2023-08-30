package app.mat.movie.domain.useCase.show

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.VideoDto

interface GetShowVideosUseCase {
    suspend operator fun invoke(
        showId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<List<VideoDto>>
}