package app.mat.movie.domain.userCase.show

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.DetailPresentable
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import kotlinx.coroutines.flow.Flow

interface GetOnTheAirShowsUseCase {
    operator fun invoke(
        deviceLanguage: DeviceLanguageDto,
        filtered: Boolean
    ): Flow<PagingData<DetailPresentable>>
}

