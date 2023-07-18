package app.mat.movie.domain.userCase.movie

import app.mat.movie.common.Resource

interface GetMovieReviewsCountUseCase {
    suspend operator fun invoke(
        movieId: Int
    ): Resource<Int>
}