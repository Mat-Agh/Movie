package app.mat.movie.domain.useCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieReviewsCountUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieReviewsCountUseCase {
    override suspend operator fun invoke(
        movieId: Int
    ): Resource<Int> {
        val response = movieRepository
            .movieReview(
                movieId
            )
            .awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()

            is Resource.Success -> {
                val reviewsCount = response.data?.totalResults ?: 0
                Resource.Success(
                    reviewsCount
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