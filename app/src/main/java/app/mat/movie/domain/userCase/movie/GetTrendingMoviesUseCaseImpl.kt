package app.mat.movie.domain.userCase.movie

import androidx.paging.PagingData
import androidx.paging.map
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.Presentable
import app.mat.movie.data.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetTrendingMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetTrendingMoviesUseCase {
    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    override operator fun invoke(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<Presentable>> = movieRepository.trendingMovies(
        deviceLanguage
    ).mapLatest { data -> data.map { it } }
}