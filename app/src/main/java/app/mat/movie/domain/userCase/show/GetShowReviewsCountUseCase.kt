package app.mat.movie.domain.userCase.show

import app.mat.movie.common.Resource

interface GetShowReviewsCountUseCase {
    suspend operator fun invoke(
        showId: Int
    ): Resource<Int>
}