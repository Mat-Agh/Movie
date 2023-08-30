package app.mat.movie.domain.useCase.movie

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.movie.MovieFavoriteEntity
import kotlinx.coroutines.flow.Flow

interface GetFavoritesMoviesUseCase {
    operator fun invoke(): Flow<PagingData<MovieFavoriteEntity>>
}