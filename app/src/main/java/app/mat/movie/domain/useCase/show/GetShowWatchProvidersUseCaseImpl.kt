package app.mat.movie.domain.useCase.show

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.WatchProvidersDto
import app.mat.movie.data.repository.ShowRepository
import javax.inject.Inject

class GetShowWatchProvidersUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetShowWatchProvidersUseCase {
    override suspend operator fun invoke(
        tvShowId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<WatchProvidersDto?> {
        val response = tvShowRepository.watchProviders(
            showId = tvShowId
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()

            is Resource.Success -> {
                val results = response.data?.results
                val providers = results?.getOrElse(
                    deviceLanguage.region
                ) {
                    null
                }

                Resource.Success(
                    providers
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