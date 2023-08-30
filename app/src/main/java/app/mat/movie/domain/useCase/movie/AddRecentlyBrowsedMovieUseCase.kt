package app.mat.movie.domain.useCase.movie

import app.mat.movie.data.remote.dto.movie.MovieDetailsDto

interface AddRecentlyBrowsedMovieUseCase {
    operator fun invoke(
        details: MovieDetailsDto
    )
}
