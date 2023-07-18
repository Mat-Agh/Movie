package app.mat.movie.domain.userCase.show

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.data.repository.SeasonRepository
import javax.inject.Inject

class GetSeasonsVideosUseCaseImpl @Inject constructor(
    private val seasonRepository: SeasonRepository
) : GetSeasonsVideosUseCase {
    override suspend operator fun invoke(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<List<VideoDto>> {
        val response = seasonRepository.seasonVideos(
            tvShowId = tvShowId,
            seasonNumber = seasonNumber,
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()

            is Resource.Success -> {
                val videos = response.data?.results?.sortedWith(
                    compareBy<VideoDto> { video ->
                        video.language == deviceLanguage.languageCode
                    }.thenByDescending { video ->
                        video.publishedAt
                    }
                )
                Resource.Success(
                    videos
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