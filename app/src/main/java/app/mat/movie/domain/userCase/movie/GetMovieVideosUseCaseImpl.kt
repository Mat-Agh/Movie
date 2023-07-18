package app.mat.movie.domain.userCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieVideosUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieVideosUseCase {
    override suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<List<VideoDto>> {
        val response = movieRepository.getMovieVideos(
            movieId = movieId,
            isoCode = deviceLanguage.languageCode
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                val results = response.data?.results
                val videos = results?.sortedWith(
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