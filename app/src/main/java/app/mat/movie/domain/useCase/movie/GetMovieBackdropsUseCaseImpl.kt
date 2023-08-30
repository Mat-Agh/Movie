package app.mat.movie.domain.useCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.ImageDto
import app.mat.movie.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieBackdropsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieBackdropsUseCase {
    override suspend operator fun invoke(
        movieId: Int
    ): Resource<List<ImageDto>> {
        val response = movieRepository.movieImages(
            movieId
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()

            is Resource.Success -> {
                val backdrops = response.data?.backdrops ?: emptyList()
                Resource.Success(
                    backdrops
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