package app.mat.movie.domain.userCase.movie

import kotlinx.coroutines.flow.Flow

interface GetFavoriteMoviesIdsUseCase {
    operator fun invoke(): Flow<List<Int>>
}