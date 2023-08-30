package app.mat.movie.domain.useCase.movie

import app.mat.movie.data.repository.RecentlyBrowsedRepository
import javax.inject.Inject

class ClearRecentlyBrowsedMoviesUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) : ClearRecentlyBrowsedMoviesUseCase {
    override operator fun invoke() = recentlyBrowsedRepository.clearRecentlyBrowsedMovies()
}