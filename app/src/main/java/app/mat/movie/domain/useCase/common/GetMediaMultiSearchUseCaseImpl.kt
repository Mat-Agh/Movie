package app.mat.movie.domain.useCase.common

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.SearchResultDto
import app.mat.movie.data.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaMultiSearchUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : GetMediaMultiSearchUseCase {
    override operator fun invoke(
        query: String,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<SearchResultDto>> = searchRepository.multiSearch(
        query = query,
        deviceLanguage = deviceLanguage
    )
}