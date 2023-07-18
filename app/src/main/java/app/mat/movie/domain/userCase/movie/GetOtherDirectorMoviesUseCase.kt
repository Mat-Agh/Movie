package app.mat.movie.domain.userCase.movie

import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.CrewMemberDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.movie.MovieDto
import kotlinx.coroutines.flow.Flow

interface GetOtherDirectorMoviesUseCase {
    operator fun invoke(
        mainDirector: CrewMemberDto,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<MovieDto>>
}