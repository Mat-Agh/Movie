package app.mat.movie.data.repository

import app.mat.movie.data.remote.api.show.ShowApiHelper
import app.mat.movie.data.remote.dto.common.AggregatedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ImagesResponseDto
import app.mat.movie.data.remote.dto.common.SeasonDetailsDto
import app.mat.movie.data.remote.dto.common.VideosResponseDto
import app.mat.movie.data.remote.dto.show.SeasonsResponseDto
import app.mat.movie.data.repository.SeasonRepository
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonRepositoryImpl @Inject constructor(
    private val showApiHelper: ShowApiHelper
) : SeasonRepository {
    override fun getTvSeason(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto
    ): Call<SeasonsResponseDto> = showApiHelper.getTvSeasons(
        tvShowId,
        seasonNumber,
        deviceLanguage.languageCode
    )

    override fun seasonDetails(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto
    ): Call<SeasonDetailsDto> = showApiHelper.getSeasonDetails(
        tvShowId,
        seasonNumber,
        deviceLanguage.languageCode
    )

    override fun episodesImage(
        tvShowId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): Call<ImagesResponseDto> = showApiHelper.getEpisodeImages(
        tvShowId,
        seasonNumber,
        episodeNumber
    )

    override fun seasonVideos(
        tvShowId: Int,
        seasonNumber: Int,
        isoCode: String
    ): Call<VideosResponseDto> = showApiHelper.getSeasonVideos(
        tvShowId,
        seasonNumber,
        isoCode
    )

    override fun seasonCredits(
        tvShowId: Int,
        seasonNumber: Int,
        isoCode: String
    ): Call<AggregatedCreditsDto> = showApiHelper.getSeasonCredits(
        tvShowId,
        seasonNumber,
        isoCode
    )
}