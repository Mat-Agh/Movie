package app.mat.movie.domain.userCase.show

import app.mat.movie.data.remote.dto.common.GenreDto
import kotlinx.coroutines.flow.Flow

interface GetShowGenresUseCase {
    operator fun invoke(): Flow<List<GenreDto>>
}