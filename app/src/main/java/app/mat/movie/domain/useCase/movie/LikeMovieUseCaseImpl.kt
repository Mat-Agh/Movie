package app.mat.movie.domain.useCase.movie

import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.repository.FavoritesRepository
import javax.inject.Inject

class LikeMovieUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : LikeMovieUseCase {
    override operator fun invoke(
        details: MovieDetailsDto
    ) = favoritesRepository.likeMovie(
        details
    )
}
