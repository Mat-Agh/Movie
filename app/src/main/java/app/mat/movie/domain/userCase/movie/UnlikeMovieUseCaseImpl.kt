package app.mat.movie.domain.userCase.movie

import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.repository.FavoritesRepository
import javax.inject.Inject

class UnlikeMovieUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : UnlikeMovieUseCase {
    override operator fun invoke(
        details: MovieDetailsDto
    ) = favoritesRepository.unlikeMovie(
        details
    )
}
