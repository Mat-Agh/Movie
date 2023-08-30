package app.mat.movie.domain.useCase.show

import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.repository.FavoritesRepository
import javax.inject.Inject

class UnlikeShowUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : UnlikeShowUseCase {
    override operator fun invoke(
        details: ShowDetailsDto
    ) = favoritesRepository.unlikeTvShows(
        details
    )
}