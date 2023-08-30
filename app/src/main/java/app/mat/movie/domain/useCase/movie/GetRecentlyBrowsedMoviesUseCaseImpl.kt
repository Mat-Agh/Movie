package app.mat.movie.domain.useCase.movie

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.movie.RecentlyBrowsedMovieEntity
import app.mat.movie.data.repository.RecentlyBrowsedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentlyBrowsedMoviesUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository,
) : GetRecentlyBrowsedMoviesUseCase {
    override operator fun invoke(): Flow<PagingData<RecentlyBrowsedMovieEntity>> = recentlyBrowsedRepository.recentlyBrowsedMovies()
}
