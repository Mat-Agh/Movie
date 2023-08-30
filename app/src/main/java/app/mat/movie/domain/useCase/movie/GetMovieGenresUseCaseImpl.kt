package app.mat.movie.domain.useCase.movie

import app.mat.movie.data.remote.dto.common.GenreDto
import app.mat.movie.data.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMovieGenresUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) : GetMovieGenresUseCase {
    override operator fun invoke(): Flow<List<GenreDto>> = configRepository.getMoviesGenres()
}