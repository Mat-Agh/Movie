package app.mat.movie.domain.useCase.show

import androidx.paging.PagingData
import app.mat.movie.common.type.RelationType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.show.ShowDto
import kotlinx.coroutines.flow.Flow

interface GetRelatedShowsOfTypeUseCase {
    operator fun invoke(
        tvShowId: Int,
        relationType: RelationType,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<ShowDto>>
}