package app.mat.movie.domain.useCase.common

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.SearchResultDto
import kotlinx.coroutines.flow.Flow

interface GetMediaMultiSearchUseCase {
    operator fun invoke(
        query: String,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<SearchResultDto>>
}