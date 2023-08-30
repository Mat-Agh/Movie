package app.mat.movie.domain.useCase.show

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.ImageDto

interface GetShowImagesUseCase {
    suspend operator fun invoke(
        tvShowId: Int
    ): Resource<List<ImageDto>>
}