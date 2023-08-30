package app.mat.movie.domain.useCase.show

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.data.repository.ShowRepository
import javax.inject.Inject

class GetShowVideosUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetShowVideosUseCase {
    override suspend operator fun invoke(
        showId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<List<VideoDto>> {
        val response = tvShowRepository.showVideos(
            showId = showId,
            standardCode = deviceLanguage.languageCode
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                val videos = response.data?.results?.sortedWith(
                    compareBy(
                        { video -> video.official },
                        { video -> video.publishedAt }
                    )
                ) ?: emptyList()
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