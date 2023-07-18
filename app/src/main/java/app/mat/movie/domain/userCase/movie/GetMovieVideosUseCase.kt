package app.mat.movie.domain.userCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.VideoDto

interface GetMovieVideosUseCase {
    suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<List<VideoDto>>
}