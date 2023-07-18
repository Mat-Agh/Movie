package app.mat.movie.domain.userCase.show

import app.mat.movie.data.remote.dto.common.GenreDto
import app.mat.movie.data.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShowGenresUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) : GetShowGenresUseCase {
    override operator fun invoke(): Flow<List<GenreDto>> = configRepository.getTvShowGenres()
}