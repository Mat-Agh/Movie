package app.mat.movie.domain.userCase.show

import androidx.paging.PagingData
import app.mat.movie.common.type.ShowType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow

interface GetShowOfTypeUseCase {
    operator fun invoke(
        type: ShowType,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<Presentable>>
}