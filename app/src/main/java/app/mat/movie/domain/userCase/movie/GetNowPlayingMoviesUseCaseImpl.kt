package app.mat.movie.domain.userCase.movie

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import app.mat.movie.data.local.entity.movie.MovieDetailEntity
import app.mat.movie.data.remote.dto.common.DetailPresentable
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetNowPlayingMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetNowPlayingMoviesUseCase {
    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    override operator fun invoke(
        deviceLanguage: DeviceLanguageDto,
        filtered: Boolean
    ): Flow<PagingData<DetailPresentable>> = movieRepository.nowPlayingMovies(
        deviceLanguage
    ).mapLatest { data ->
        if (filtered) data.filterCompleteInfo() else data
    }.mapLatest { data -> data.map { it } }

    private fun PagingData<MovieDetailEntity>.filterCompleteInfo(): PagingData<MovieDetailEntity> = filter { movie ->
        movie.run {
            !backdropPath.isNullOrEmpty() &&
                    !posterPath.isNullOrEmpty() &&
                    title.isNotEmpty() &&
                    overView.isNotEmpty()
        }
    }
}
