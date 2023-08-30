package app.mat.movie.domain.useCase.movie

import kotlinx.coroutines.flow.Flow

interface GetFavoritesMovieCountUseCase {
    operator fun invoke(): Flow<Int>
}