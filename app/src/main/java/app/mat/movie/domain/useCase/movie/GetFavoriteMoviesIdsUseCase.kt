package app.mat.movie.domain.useCase.movie

import kotlinx.coroutines.flow.Flow

interface GetFavoriteMoviesIdsUseCase {
    operator fun invoke(): Flow<List<Int>>
}