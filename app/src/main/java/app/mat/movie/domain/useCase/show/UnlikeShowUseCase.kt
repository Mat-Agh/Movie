package app.mat.movie.domain.useCase.show

import app.mat.movie.data.remote.dto.show.ShowDetailsDto

interface UnlikeShowUseCase {
    operator fun invoke(
        details: ShowDetailsDto
    )
}