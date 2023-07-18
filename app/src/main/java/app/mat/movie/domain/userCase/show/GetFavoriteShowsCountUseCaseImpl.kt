package app.mat.movie.domain.userCase.show

import app.mat.movie.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteShowsCountUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetFavoriteShowsCountUseCase {
    override operator fun invoke(): Flow<Int> = favoritesRepository.getFavoriteTvShowsCount()
}