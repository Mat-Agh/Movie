package app.mat.movie.data.repository

import app.mat.movie.common.util.ImageUrlParser
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.GenreDto
import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun isInitialized(): Flow<Boolean>

    fun updateLocale()

    fun getSpeechToTextAvailable(): Flow<Boolean>

    fun getCameraAvailable(): Flow<Boolean>

    fun getDeviceLanguage(): Flow<DeviceLanguageDto>

    fun getImageUrlParser(): Flow<ImageUrlParser?>

    fun getMoviesGenres(): Flow<List<GenreDto>>

    fun getTvShowGenres(): Flow<List<GenreDto>>

    fun getAllMoviesWatchProviders(): Flow<List<ProviderSourceDto>>

    fun getAllTvShowWatchProviders(): Flow<List<ProviderSourceDto>>
}