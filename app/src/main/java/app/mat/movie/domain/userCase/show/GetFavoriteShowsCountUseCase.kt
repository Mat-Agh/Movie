package app.mat.movie.domain.userCase.show

import kotlinx.coroutines.flow.Flow

interface GetFavoriteShowsCountUseCase {
    operator fun invoke(): Flow<Int>
}