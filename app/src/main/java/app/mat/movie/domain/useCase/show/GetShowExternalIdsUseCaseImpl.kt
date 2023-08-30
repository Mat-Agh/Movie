package app.mat.movie.domain.useCase.show

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.type.ExternalContentType
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.data.repository.ShowRepository
import javax.inject.Inject

class GetShowExternalIdsUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetShowExternalIdsUseCase {
    override suspend operator fun invoke(
        tvShowId: Int
    ): Resource<List<ExternalIdsResource>> {
        val response = tvShowRepository.getExternalIds(
            tvShowId
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                val externalIds = response.data?.toExternalIdList(
                    type = ExternalContentType.Tv
                )
                Resource.Success(
                    externalIds
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