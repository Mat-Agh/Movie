package app.mat.movie.domain.userCase.show

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.show.ShowFavoriteEntity
import app.mat.movie.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesShowsUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetFavoritesShowsUseCase {
    override operator fun invoke(): Flow<PagingData<ShowFavoriteEntity>> = favoritesRepository.favoriteTvShows()
}
