package app.mat.movie.domain.userCase.common

import kotlinx.coroutines.flow.Flow

interface GetCameraAvailableUseCase {
    operator fun invoke(): Flow<Boolean>
}