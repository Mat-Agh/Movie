package app.mat.movie.domain.userCase.movie

import app.mat.movie.data.remote.dto.movie.MovieDetailsDto

interface UnlikeMovieUseCase {
    operator fun invoke(
        details: MovieDetailsDto
    )
}
