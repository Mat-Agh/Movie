package app.mat.movie.domain.userCase.show

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import app.mat.movie.data.local.entity.show.ShowDetailsEntity
import app.mat.movie.data.remote.dto.common.DetailPresentable
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.repository.ShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetOnTheAirShowsUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetOnTheAirShowsUseCase {
    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    override operator fun invoke(
        deviceLanguage: DeviceLanguageDto,
        filtered: Boolean
    ): Flow<PagingData<DetailPresentable>> = tvShowRepository.onTheAirShows(
        deviceLanguage
    ).mapLatest { data ->
        if (filtered) {
            data.filterCompleteInfo()
        } else {
            data
        }
    }.mapLatest { data -> data.map { it } }

    private fun PagingData<ShowDetailsEntity>.filterCompleteInfo(): PagingData<ShowDetailsEntity> = filter { tvShow ->
        tvShow.run {
            !backdropPath.isNullOrEmpty() &&
                    !posterPath.isNullOrEmpty() &&
                    title.isNotEmpty() &&
                    overView.isNotEmpty()
        }
    }
}

