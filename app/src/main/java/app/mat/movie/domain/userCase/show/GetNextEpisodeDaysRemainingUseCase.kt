package app.mat.movie.domain.userCase.show

import java.util.Date

interface GetNextEpisodeDaysRemainingUseCase {
    operator fun invoke(
        nextEpisodeAirDate: Date
    ): Long
}