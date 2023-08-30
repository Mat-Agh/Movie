package app.mat.movie.domain.useCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.CreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieCreditUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieCreditUseCase {
    override suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<CreditsDto> = movieRepository.movieCredits(
        movieId = movieId,
        isoCode = deviceLanguage.languageCode
    ).awaitApiResponse()
}