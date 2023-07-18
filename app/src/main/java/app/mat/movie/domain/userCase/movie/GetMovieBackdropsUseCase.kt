package app.mat.movie.domain.userCase.movie

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.ImageDto

interface GetMovieBackdropsUseCase {
    suspend operator fun invoke(
        movieId: Int
    ): Resource<List<ImageDto>>
}