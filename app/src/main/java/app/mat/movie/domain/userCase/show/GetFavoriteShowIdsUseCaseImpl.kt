package app.mat.movie.domain.userCase.show

import app.mat.movie.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteShowIdsUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetFavoriteShowIdsUseCase {
    override operator fun invoke(): Flow<List<Int>> = favoritesRepository.getFavoriteTvShowsIds()
}