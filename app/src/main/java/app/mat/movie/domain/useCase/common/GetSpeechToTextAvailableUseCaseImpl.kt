package app.mat.movie.domain.useCase.common

import app.mat.movie.data.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpeechToTextAvailableUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) : GetSpeechToTextAvailableUseCase {
    override operator fun invoke(): Flow<Boolean> = configRepository.getSpeechToTextAvailable()
}