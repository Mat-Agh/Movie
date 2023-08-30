package app.mat.movie.domain.useCase.show

import app.mat.movie.common.Resource
import app.mat.movie.common.awaitApiResponse
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.repository.ShowRepository
import javax.inject.Inject

class GetShowDetailsUseCaseImpl @Inject constructor(
    private val tvShowRepository: ShowRepository
) : GetShowDetailsUseCase {
    override suspend operator fun invoke(
        tvShowId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Resource<ShowDetailsDto> = tvShowRepository.getShowDetails(
        showId = tvShowId,
        deviceLanguage = deviceLanguage
    ).awaitApiResponse()
}