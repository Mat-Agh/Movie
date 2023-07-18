package app.mat.movie.domain.userCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.type.ExternalContentType
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieExternalIdsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieExternalIdsUseCase {
    override suspend operator fun invoke(
        movieId: Int
    ): Resource<List<ExternalIdsResource>> {
        val response = movieRepository.getExternalIds(
            movieId
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()

            is Resource.Success -> {
                val ids = response.data?.toExternalIdList(
                    ExternalContentType.Movie
                )
                Resource.Success(
                    ids
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