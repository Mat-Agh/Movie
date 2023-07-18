package app.mat.movie.domain.userCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.type.ExternalIdsResource

interface GetMovieExternalIdsUseCase {
    suspend operator fun invoke(
        movieId: Int
    ): Resource<List<ExternalIdsResource>>
}