package app.mat.movie.domain.useCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieCollectionDto
import app.mat.movie.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieCollectionUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieCollectionUseCase {
    override suspend operator fun invoke(
        collectionId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<MovieCollectionDto?> {
        val response = movieRepository.collection(
            collectionId = collectionId,
            isoCode = deviceLanguage.languageCode
        ).awaitApiResponse()

        return when (response) {
            is Resource.Loading -> Resource.Loading()

            is Resource.Success -> {
                val collection = response.data?.let { collection ->
                    val name = collection.name
                    val parts = collection.parts

                    MovieCollectionDto(
                        name = name,
                        parts = parts
                    )
                }
                Resource.Success(
                    collection
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