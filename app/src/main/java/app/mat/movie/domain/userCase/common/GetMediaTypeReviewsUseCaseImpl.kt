package app.mat.movie.domain.userCase.common

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.ReviewDto
import app.mat.movie.data.remote.type.MediaType
import app.mat.movie.data.repository.MovieRepository
import app.mat.movie.data.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetMediaTypeReviewsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: ShowRepository
) : GetMediaTypeReviewsUseCase {
    override operator fun invoke(
        mediaId: Int,
        mediaType: MediaType
    ): Flow<PagingData<ReviewDto>> = when (mediaType) {
        MediaType.Movie -> movieRepository.movieReviews(
            mediaId
        )

        MediaType.Tv -> tvShowRepository.showReviews(
            mediaId
        )

        else -> emptyFlow()
    }
}