package app.mat.movie.domain.useCase.common

import kotlinx.coroutines.flow.Flow

interface GetSpeechToTextAvailableUseCase {
    operator fun invoke(): Flow<Boolean>
}