package app.mat.movie.domain.useCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<MovieDetailsDto>
}
