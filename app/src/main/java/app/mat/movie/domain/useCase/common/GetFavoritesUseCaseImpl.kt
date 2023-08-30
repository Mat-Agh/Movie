package app.mat.movie.domain.useCase.common

import androidx.paging.PagingData
import androidx.paging.map
import app.mat.movie.common.type.FavoriteType
import app.mat.movie.data.remote.dto.common.Presentable
import app.mat.movie.data.repository.FavoritesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


class GetFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetFavoritesUseCase {
    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    override operator fun invoke(
        type: FavoriteType
    ): Flow<PagingData<Presentable>> {
        val favorites = when (type) {
            FavoriteType.Movie -> favoritesRepository.favoriteMovies()
            FavoriteType.TvShow -> favoritesRepository.favoriteTvShows()
        }.mapLatest { data -> data.map { it } }

        return favorites
    }
}