package app.mat.movie.domain.useCase.common

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.ReviewDto
import app.mat.movie.data.remote.type.MediaType
import kotlinx.coroutines.flow.Flow

interface GetMediaTypeReviewsUseCase {
    operator fun invoke(
        mediaId: Int,
        mediaType: MediaType
    ): Flow<PagingData<ReviewDto>>
}