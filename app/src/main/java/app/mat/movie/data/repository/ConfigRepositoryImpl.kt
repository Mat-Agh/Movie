package app.mat.movie.data.repository

import app.mat.movie.common.util.ImageUrlParser
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.GenreDto
import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import app.mat.movie.domain.configuration.DataSourceConfiguration
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val dataSourceConfiguration: DataSourceConfiguration
) : ConfigRepository {
    override fun isInitialized(): Flow<Boolean> = dataSourceConfiguration.isInitialized

    override fun updateLocale() = dataSourceConfiguration.updateLocale()

    override fun getSpeechToTextAvailable(): Flow<Boolean> = dataSourceConfiguration.speechToTextAvailable

    override fun getCameraAvailable(): Flow<Boolean> = dataSourceConfiguration.hasCamera

    override fun getDeviceLanguage(): Flow<DeviceLanguageDto> = dataSourceConfiguration.deviceLanguage

    override fun getImageUrlParser(): Flow<ImageUrlParser?> = dataSourceConfiguration.imageUrlParser

    override fun getMoviesGenres(): Flow<List<GenreDto>> = dataSourceConfiguration.movieGenres

    override fun getTvShowGenres(): Flow<List<GenreDto>> = dataSourceConfiguration.tvShowGenres

    override fun getAllMoviesWatchProviders(): Flow<List<ProviderSourceDto>> = dataSourceConfiguration.movieWatchProviders

    override fun getAllTvShowWatchProviders(): Flow<List<ProviderSourceDto>> = dataSourceConfiguration.tvShowWatchProviders
}