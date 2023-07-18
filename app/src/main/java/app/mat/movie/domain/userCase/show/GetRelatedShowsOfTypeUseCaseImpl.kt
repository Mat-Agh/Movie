package app.mat.movie.domain.userCase.show

import androidx.paging.PagingData
import app.mat.movie.common.type.RelationType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.show.ShowDto
import app.mat.movie.data.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRelatedShowsOfTypeUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetRelatedShowsOfTypeUseCase {
    override operator fun invoke(
        tvShowId: Int,
        relationType: RelationType,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowDto>> {
        return when (relationType) {
            RelationType.Similar -> {
                tvShowRepository.similarShows(
                    showId = tvShowId,
                    deviceLanguage = deviceLanguage
                )
            }

            RelationType.Recommended -> {
                tvShowRepository.showsRecommendations(
                    showId = tvShowId,
                    deviceLanguage = deviceLanguage
                )
            }
        }
    }
}