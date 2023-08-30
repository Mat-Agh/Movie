package app.mat.movie.domain.useCase.show

import androidx.paging.PagingData
import androidx.paging.map
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.Presentable
import app.mat.movie.data.repository.ShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetTopRatedShowsUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetTopRatedShowsUseCase {
    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    override operator fun invoke(
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<Presentable>> = tvShowRepository.topRatedShows(
        deviceLanguage
    ).mapLatest { data -> data.map { it } }
}