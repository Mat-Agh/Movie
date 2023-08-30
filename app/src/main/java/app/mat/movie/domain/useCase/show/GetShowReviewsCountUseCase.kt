package app.mat.movie.domain.useCase.show

import app.mat.movie.common.Resource

interface GetShowReviewsCountUseCase {
    suspend operator fun invoke(
        showId: Int
    ): Resource<Int>
}