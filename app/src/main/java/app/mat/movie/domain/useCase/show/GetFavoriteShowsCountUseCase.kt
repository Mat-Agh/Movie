package app.mat.movie.domain.useCase.show

import kotlinx.coroutines.flow.Flow

interface GetFavoriteShowsCountUseCase {
    operator fun invoke(): Flow<Int>
}