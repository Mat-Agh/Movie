package app.mat.movie.domain.userCase.show

import app.mat.movie.data.remote.dto.show.ShowDetailsDto

interface LikeShowUseCase {
    operator fun invoke(
        details: ShowDetailsDto
    )
}