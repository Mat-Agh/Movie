package app.mat.movie.domain.userCase.common

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.ImageDto
import app.mat.movie.data.repository.SeasonRepository
import javax.inject.Inject

class GetEpisodeStillsUseCaseImpl @Inject constructor(
    private val seasonRepository: SeasonRepository
) : GetEpisodeStillsUseCase {
    override suspend operator fun invoke(
        tvShowId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): Resource<List<ImageDto>> {
        val response = seasonRepository.episodesImage(
            tvShowId = tvShowId,
            seasonNumber = seasonNumber,
            episodeNumber = episodeNumber
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()

            is Resource.Success -> {
                val stills = response.data?.stills ?: emptyList()

                Resource.Success(
                    stills
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