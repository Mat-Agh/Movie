package app.mat.movie.domain.useCase.movie

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.movie.MovieFavoriteEntity
import app.mat.movie.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesMoviesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetFavoritesMoviesUseCase {
    override operator fun invoke(): Flow<PagingData<MovieFavoriteEntity>> = favoritesRepository.favoriteMovies()
}