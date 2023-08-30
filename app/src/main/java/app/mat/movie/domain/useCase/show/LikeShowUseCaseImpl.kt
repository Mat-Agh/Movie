package app.mat.movie.domain.useCase.show

import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.repository.FavoritesRepository
import javax.inject.Inject

class LikeShowUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : LikeShowUseCase {
    override operator fun invoke(
        details: ShowDetailsDto
    ) = favoritesRepository.likeTvShow(
        details
    )
}