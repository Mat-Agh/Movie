package app.mat.movie.domain.configuration

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import app.mat.movie.common.onException
import app.mat.movie.common.onSuccess
import app.mat.movie.common.request
import app.mat.movie.common.util.ImageUrlParser
import app.mat.movie.data.remote.api.movie.MovieApiHelper
import app.mat.movie.data.remote.api.other.OtherApiHelper
import app.mat.movie.data.remote.api.show.ShowApiHelper
import app.mat.movie.data.remote.dto.common.ConfigDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.GenreDto
import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSourceConfiguration @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val movieApiHelper: MovieApiHelper,
    private val showApiHelper: ShowApiHelper,
    private val otherApiHelper: OtherApiHelper
) {
    //region Variables
    private val _config: MutableStateFlow<ConfigDto?> = MutableStateFlow(null)

    val speechToTextAvailable: Flow<Boolean> = flow {
        val packageManager = context.packageManager
        val activities: List<*> = packageManager.queryIntentActivities(
            Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            ),
            0
        )

        emit(
            activities.isNotEmpty()
        )
    }.flowOn(defaultDispatcher)

    val hasCamera: Flow<Boolean> = flow {
        val packageManager = context.packageManager
        val hasCamera = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
        emit(
            hasCamera
        )
    }

    private val _deviceLanguage: MutableStateFlow<DeviceLanguageDto> = MutableStateFlow(
        getCurrentDeviceLanguage()
    )

    val deviceLanguage: StateFlow<DeviceLanguageDto> = _deviceLanguage.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val imageUrlParser: Flow<ImageUrlParser?> = _config.mapLatest { config ->
        if (config != null) {
            ImageUrlParser(
                config.imagesConfig
            )
        } else null
    }.flowOn(
        defaultDispatcher
    )

    private val _movieGenres: MutableStateFlow<List<GenreDto>> = MutableStateFlow(
        emptyList()
    )
    val movieGenres: StateFlow<List<GenreDto>> = _movieGenres.asStateFlow()

    private val _tvShowGenres: MutableStateFlow<List<GenreDto>> = MutableStateFlow(
        emptyList()
    )
    val tvShowGenres: StateFlow<List<GenreDto>> = _tvShowGenres.asStateFlow()

    private val _movieWatchProviders: MutableStateFlow<List<ProviderSourceDto>> = MutableStateFlow(
        emptyList()
    )
    val movieWatchProviders: StateFlow<List<ProviderSourceDto>> = _movieWatchProviders.asStateFlow()

    private val _tvShowWatchProviders: MutableStateFlow<List<ProviderSourceDto>> = MutableStateFlow(
        emptyList()
    )
    val tvShowWatchProviders: StateFlow<List<ProviderSourceDto>> = _tvShowWatchProviders.asStateFlow()

    val isInitialized: StateFlow<Boolean> = combine(
        _config,
        _movieGenres,
        _tvShowGenres,
        _movieWatchProviders,
        _tvShowWatchProviders
    ) { imageUrlParser, movieGenres, tvShowGenres, movieWatchProviders, tvSeriesWatchProviders ->
        val imageUrlParserInit = imageUrlParser != null
        val movieGenresInit = movieGenres.isNotEmpty()
        val tvSeriesGenresInit = tvShowGenres.isNotEmpty()
        val movieWatchProvidersInit = movieWatchProviders.isNotEmpty()
        val tvSeriesWatchProvidersInit = tvSeriesWatchProviders.isNotEmpty()

        listOf(
            imageUrlParserInit,
            movieGenresInit,
            tvSeriesGenresInit,
            movieWatchProvidersInit,
            tvSeriesWatchProvidersInit
        ).all { init -> init }
    }.stateIn(
        scope = externalScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 10
        ),
        initialValue = false
    )

    //endregion Variables

    //region Initialization
    fun init() {
        movieApiHelper.getConfig().request { response ->
            response.onSuccess {
                externalScope.launch(
                    defaultDispatcher
                ) {
                    val config = data
                    _config.emit(config)
                }
            }.onException {
                TODO(
                    "Add event listener here"
                )
            }
        }

        externalScope.launch(
            defaultDispatcher
        ) {
            deviceLanguage.collectLatest { deviceLanguage ->
                movieApiHelper.getMoviesGenres(
                    standardCode = deviceLanguage.languageCode
                ).request { response ->
                    response.onSuccess {
                        externalScope.launch(
                            defaultDispatcher
                        ) {
                            val movieGenres = data?.genres

                            _movieGenres.emit(movieGenres ?: emptyList())
                        }
                    }.onException {
                        TODO(
                            "Add event listener here"
                        )
                    }
                }

                movieApiHelper.getAllMoviesWatchProviders(
                    standardCode = deviceLanguage.languageCode,
                    region = deviceLanguage.region
                ).request { response ->
                    response.onSuccess {
                        externalScope.launch(
                            defaultDispatcher
                        ) {
                            val watchProviders = data?.results?.sortedBy { provider ->
                                provider.displayPriority
                            }

                            _movieWatchProviders.emit(watchProviders ?: emptyList())
                        }
                    }.onException {
                        TODO(
                            "Add event listener here"
                        )
                    }
                }
            }
        }

        showApiHelper.getConfig().request { response ->
            response.onSuccess {
                externalScope.launch(
                    defaultDispatcher
                ) {
                    val config = data
                    _config.emit(config)
                }
            }.onException {
                TODO(
                    "Add event listener here"
                )
            }
        }

        externalScope.launch(
            defaultDispatcher
        ) {
            deviceLanguage.collectLatest { deviceLanguage ->
                showApiHelper.getShowsGenres(
                    standardCode = deviceLanguage.languageCode
                ).request { response ->
                    response.onSuccess {
                        externalScope.launch(
                            defaultDispatcher
                        ) {
                            val tvSeriesGenres = data?.genres

                            _tvShowGenres.emit(tvSeriesGenres ?: emptyList())
                        }
                    }.onException {
                        TODO(
                            "Add event listener here!"
                        )
                    }
                }

                showApiHelper.getAllShowsWatchProviders(
                    standardCode = deviceLanguage.languageCode,
                    region = deviceLanguage.region
                ).request { response ->
                    response.onSuccess {
                        externalScope.launch(
                            defaultDispatcher
                        ) {
                            val watchProviders = data?.results?.sortedBy { provider ->
                                provider.displayPriority
                            }

                            _tvShowWatchProviders.emit(watchProviders ?: emptyList())
                        }
                    }.onException {
                        TODO(
                            "Add event listener here!"
                        )
                    }
                }
            }
        }
    }
    //endregion Initialization

    //region Public Methods
    fun updateLocale() {
        externalScope.launch {
            val deviceLanguage = getCurrentDeviceLanguage()
            _deviceLanguage.emit(deviceLanguage)
        }
    }
    //endregion Public Methods

    //region Private Methods
    private fun getCurrentDeviceLanguage(): DeviceLanguageDto {
        val locale = Locale.getDefault()

        val languageCode = locale.toLanguageTag().ifEmpty { DeviceLanguageDto.default.languageCode }
        val region = locale.country.ifEmpty { DeviceLanguageDto.default.region }

        return DeviceLanguageDto(
            languageCode = languageCode,
            region = region
        )
    }
    //endregion Private Methods

}