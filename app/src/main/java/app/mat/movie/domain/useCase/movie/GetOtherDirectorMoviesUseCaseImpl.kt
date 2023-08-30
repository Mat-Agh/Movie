package app.mat.movie.domain.useCase.movie

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.CrewMemberDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieDto
import app.mat.movie.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOtherDirectorMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetOtherDirectorMoviesUseCase {
    override operator fun invoke(
        mainDirector: CrewMemberDto,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDto>> = movieRepository.moviesOfDirector(
        directorId = mainDirector.id,
        deviceLanguage = deviceLanguage
    )
}