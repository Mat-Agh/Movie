package app.mat.movie.domain.userCase.show

import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.repository.RecentlyBrowsedRepository
import javax.inject.Inject

class AddRecentlyBrowsedShowUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) : AddRecentlyBrowsedShowUseCase {
    override operator fun invoke(
        details: ShowDetailsDto
    ) = recentlyBrowsedRepository.addRecentlyBrowsedShows(
        details
    )
}