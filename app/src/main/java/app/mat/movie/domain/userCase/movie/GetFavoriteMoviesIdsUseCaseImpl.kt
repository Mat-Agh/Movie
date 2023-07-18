package app.mat.movie.domain.userCase.movie

import app.mat.movie.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesIdsUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetFavoriteMoviesIdsUseCase {
    override operator fun invoke(): Flow<List<Int>> = favoritesRepository.getFavoriteMoviesIds()
}