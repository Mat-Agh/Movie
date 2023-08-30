package app.mat.movie.domain.useCase.show

import java.util.Date

interface GetNextEpisodeDaysRemainingUseCase {
    operator fun invoke(
        nextEpisodeAirDate: Date
    ): Long
}