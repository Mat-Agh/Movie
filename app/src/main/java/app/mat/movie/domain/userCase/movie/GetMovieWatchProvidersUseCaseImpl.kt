package app.mat.movie.domain.userCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.WatchProvidersDto
import app.mat.movie.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieWatchProvidersUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieWatchProvidersUseCase {
    override suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<WatchProvidersDto?> {
        val response = movieRepository.watchProviders(
            movieId = movieId
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()

            is Resource.Success -> {
                val results = response.data?.results
                val watchProviders = results?.getOrElse(
                    deviceLanguage.region
                ) { null }

                Resource.Success(
                    watchProviders
                )
            }

            is Resource.Error -> Resource.Error(
                response.error
            )

            is Resource.Exception -> Resource.Exception(
                response.exception
            )
        }
    }
}