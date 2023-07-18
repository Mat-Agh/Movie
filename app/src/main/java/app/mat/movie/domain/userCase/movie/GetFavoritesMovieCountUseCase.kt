package app.mat.movie.domain.userCase.movie

import kotlinx.coroutines.flow.Flow

interface GetFavoritesMovieCountUseCase {
    operator fun invoke(): Flow<Int>
}