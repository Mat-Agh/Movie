package app.mat.movie.domain.userCase.movie

import app.mat.movie.data.repository.RecentlyBrowsedRepository
import javax.inject.Inject

class ClearRecentlyBrowsedMoviesUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) : ClearRecentlyBrowsedMoviesUseCase {
    override operator fun invoke() = recentlyBrowsedRepository.clearRecentlyBrowsedMovies()
}