package app.mat.movie.domain.useCase.movie

import app.mat.movie.common.Resource

interface GetMovieReviewsCountUseCase {
    suspend operator fun invoke(
        movieId: Int
    ): Resource<Int>
}