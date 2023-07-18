package app.mat.movie.domain.userCase.movie

import androidx.paging.PagingData
import app.mat.movie.common.type.RelationType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieDto
import app.mat.movie.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRelatedMoviesOfTypeUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetRelatedMoviesOfTypeUseCase {
    override operator fun invoke(
        movieId: Int,
        type: RelationType,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDto>> = when (type) {
        RelationType.Similar -> {
            movieRepository.similarMovies(
                movieId = movieId,
                deviceLanguage = deviceLanguage
            )
        }

        RelationType.Recommended -> {
            movieRepository.moviesRecommendation(
                movieId = movieId,
                deviceLanguage = deviceLanguage
            )
        }
    }
}