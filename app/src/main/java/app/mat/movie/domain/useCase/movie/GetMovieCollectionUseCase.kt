package app.mat.movie.domain.useCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieCollectionDto

interface GetMovieCollectionUseCase {
    suspend operator fun invoke(
        collectionId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<MovieCollectionDto?>
}