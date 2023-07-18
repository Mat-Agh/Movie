package app.mat.movie.domain.userCase.show

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.type.ExternalIdsResource

interface GetShowExternalIdsUseCase {
    suspend operator fun invoke(
        tvShowId: Int
    ): Resource<List<ExternalIdsResource>>
}