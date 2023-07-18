package app.mat.movie.domain.userCase.show

import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetNextEpisodeDaysRemainingUseCaseImpl @Inject constructor() : GetNextEpisodeDaysRemainingUseCase {
    override operator fun invoke(
        nextEpisodeAirDate: Date
    ): Long {
        val millionSeconds = nextEpisodeAirDate.time - Calendar.getInstance().timeInMillis
        val daysDiff = TimeUnit.MILLISECONDS.toDays(
            millionSeconds
        )
        return if (daysDiff < 0) {
            0
        } else {
            daysDiff
        }
    }
}