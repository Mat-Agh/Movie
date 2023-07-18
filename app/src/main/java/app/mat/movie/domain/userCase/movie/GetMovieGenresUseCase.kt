package app.mat.movie.domain.userCase.movie

import app.mat.movie.data.remote.dto.common.GenreDto
import kotlinx.coroutines.flow.Flow


interface GetMovieGenresUseCase {
    operator fun invoke(): Flow<List<GenreDto>>
}