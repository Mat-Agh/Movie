package app.mat.movie.domain.userCase.show

import kotlinx.coroutines.flow.Flow

interface GetFavoriteShowIdsUseCase {
    operator fun invoke(): Flow<List<Int>>
}