package app.mat.movie.domain.useCase.common

import kotlinx.coroutines.flow.Flow

interface GetCameraAvailableUseCase {
    operator fun invoke(): Flow<Boolean>
}