package app.mat.movie.domain.userCase.show

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.ImageDto
import app.mat.movie.data.repository.ShowRepository
import javax.inject.Inject

class GetShowImagesUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetShowImagesUseCase {
    override suspend operator fun invoke(
        tvShowId: Int
    ): Resource<List<ImageDto>> {
        val response = tvShowRepository.showImages(
            tvShowId
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()

            is Resource.Success -> {
                val images = response.data?.backdrops ?: emptyList()
                Resource.Success(
                    images
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