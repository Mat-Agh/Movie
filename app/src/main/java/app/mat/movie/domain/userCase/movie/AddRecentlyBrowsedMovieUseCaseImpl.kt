package app.mat.movie.domain.userCase.movie

import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.repository.RecentlyBrowsedRepository
import javax.inject.Inject

class AddRecentlyBrowsedMovieUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) : AddRecentlyBrowsedMovieUseCase {
    override operator fun invoke(
        details: MovieDetailsDto
    ) = recentlyBrowsedRepository.addRecentlyBrowsedMovie(
        details
    )
}
