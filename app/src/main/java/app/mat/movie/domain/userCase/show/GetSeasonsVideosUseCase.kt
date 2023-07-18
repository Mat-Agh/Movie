package app.mat.movie.domain.userCase.show

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.VideoDto

interface GetSeasonsVideosUseCase {
    suspend operator fun invoke(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<List<VideoDto>>
}