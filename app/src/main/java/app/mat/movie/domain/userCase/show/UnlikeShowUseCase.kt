package app.mat.movie.domain.userCase.show

import app.mat.movie.data.remote.dto.show.ShowDetailsDto

interface UnlikeShowUseCase {
    operator fun invoke(
        details: ShowDetailsDto
    )
}