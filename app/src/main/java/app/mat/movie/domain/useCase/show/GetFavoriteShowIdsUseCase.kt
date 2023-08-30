package app.mat.movie.domain.useCase.show

import kotlinx.coroutines.flow.Flow

interface GetFavoriteShowIdsUseCase {
    operator fun invoke(): Flow<List<Int>>
}