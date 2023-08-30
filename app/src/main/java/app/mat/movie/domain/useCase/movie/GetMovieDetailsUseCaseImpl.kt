package app.mat.movie.domain.useCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieDetailsUseCase {
    override suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<MovieDetailsDto> = movieRepository.movieDetails(
        movieId = movieId,
        isoCode = deviceLanguage.languageCode
    ).awaitApiResponse()
}
