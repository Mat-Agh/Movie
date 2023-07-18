package app.mat.movie.data.repository

import app.mat.movie.data.remote.dto.common.AggregatedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ImagesResponseDto
import app.mat.movie.data.remote.dto.common.SeasonDetailsDto
import app.mat.movie.data.remote.dto.common.VideosResponseDto
import app.mat.movie.data.remote.dto.show.SeasonsResponseDto
import retrofit2.Call

interface SeasonRepository {
    fun getTvSeason(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Call<SeasonsResponseDto>

    fun seasonDetails(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Call<SeasonDetailsDto>

    fun episodesImage(
        tvShowId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): Call<ImagesResponseDto>

    fun seasonVideos(
        tvShowId: Int,
        seasonNumber: Int,
        isoCode: String = DeviceLanguageDto.default.languageCode
    ): Call<VideosResponseDto>

    fun seasonCredits(
        tvShowId: Int,
        seasonNumber: Int,
        isoCode: String = DeviceLanguageDto.default.languageCode
    ): Call<AggregatedCreditsDto>
}