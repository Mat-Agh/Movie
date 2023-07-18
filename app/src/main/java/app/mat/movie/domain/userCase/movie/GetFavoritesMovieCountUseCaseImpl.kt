package app.mat.movie.domain.userCase.movie

import app.mat.movie.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetFavoritesMovieCountUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetFavoritesMovieCountUseCase {
    override operator fun invoke(): Flow<Int> = favoritesRepository.getFavoriteMoviesCount()
}