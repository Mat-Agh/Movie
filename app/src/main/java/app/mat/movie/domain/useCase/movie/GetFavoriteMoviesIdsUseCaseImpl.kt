package app.mat.movie.domain.useCase.movie

import app.mat.movie.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesIdsUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetFavoriteMoviesIdsUseCase {
    override operator fun invoke(): Flow<List<Int>> = favoritesRepository.getFavoriteMoviesIds()
}