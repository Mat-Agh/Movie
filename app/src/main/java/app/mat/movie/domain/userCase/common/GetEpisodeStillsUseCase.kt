package app.mat.movie.domain.userCase.common

import app.mat.movie.common.Resource
import app.mat.movie.data.remote.dto.common.ImageDto

interface GetEpisodeStillsUseCase {
    suspend operator fun invoke(
        tvShowId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): Resource<List<ImageDto>>
}