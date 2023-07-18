package app.mat.movie.domain.userCase.show

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.repository.ShowRepository
import javax.inject.Inject

class GetShowReviewsCountUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetShowReviewsCountUseCase {
    override suspend operator fun invoke(
        showId: Int
    ): Resource<Int> = when (val response = tvShowRepository.showReview(
        showId
    ).awaitApiResponse()) {
        is Resource.Loading -> Resource.Loading()

        is Resource.Success -> {
            val reviewCount = response.data?.totalResults ?: 0
            Resource.Success(
                reviewCount
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