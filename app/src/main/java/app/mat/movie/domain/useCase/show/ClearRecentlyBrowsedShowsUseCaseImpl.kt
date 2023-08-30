package app.mat.movie.domain.useCase.show

import app.mat.movie.data.repository.RecentlyBrowsedRepository
import javax.inject.Inject

class ClearRecentlyBrowsedShowsUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) : ClearRecentlyBrowsedShowsUseCase {
    override operator fun invoke() = recentlyBrowsedRepository.clearRecentlyBrowsedTvShows()
}