package app.mat.movie.domain.userCase.common

import kotlinx.coroutines.flow.Flow

interface GetSpeechToTextAvailableUseCase {
    operator fun invoke(): Flow<Boolean>
}