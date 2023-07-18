package app.mat.movie.presentation.screen.seasons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import app.mat.movie.common.onError
import app.mat.movie.common.onException
import app.mat.movie.common.onSuccess
import app.mat.movie.data.remote.dto.common.AggregatedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ImageDto
import app.mat.movie.data.remote.dto.common.SeasonDetailsDto
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.common.GetEpisodeStillsUseCase
import app.mat.movie.domain.userCase.show.GetSeasonCreditsUseCase
import app.mat.movie.domain.userCase.show.GetSeasonDetailsUseCase
import app.mat.movie.domain.userCase.show.GetSeasonsVideosUseCase
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonDetailsViewModel @Inject constructor(
    getDeviceLanguageUseCaseImpl: GetDeviceLanguageUseCase,
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase,
    private val getSeasonsVideosUseCaseImpl: GetSeasonsVideosUseCase,
    private val getSeasonCreditsUseCase: GetSeasonCreditsUseCase,
    private val getEpisodeStillsUseCaseImpl: GetEpisodeStillsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val showId: Int = savedStateHandle[Companion.SeasonsScreenData.SEASONS_REQUIRED_ARGUMENT_SHOW_ID] ?: 0

    private val seasonNumber: Int = savedStateHandle[Companion.SeasonsScreenData.SEASONS_REQUIRED_ARGUMENT_SEASON_NUMBER] ?: 0

    private val startRoute: String = savedStateHandle[Companion.SeasonsScreenData.SEASONS_REQUIRED_ARGUMENT_SEASON_NUMBER] ?: NavigationBarGraphScreen.ShowScreen.route

    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCaseImpl()

    private val seasonDetails: MutableStateFlow<SeasonDetailsDto?> = MutableStateFlow(
        null
    )
    private val aggregatedCredits: MutableStateFlow<AggregatedCreditsDto?> = MutableStateFlow(
        null
    )
    private val episodesStills: MutableStateFlow<Map<Int, List<ImageDto>>> =
        MutableStateFlow(
            emptyMap()
        )

    private val videos: MutableStateFlow<List<VideoDto>?> = MutableStateFlow(
        null
    )

    val uiState: StateFlow<SeasonDetailsScreenUiState> = combine(
        seasonDetails, aggregatedCredits, episodesStills, videos, error
    ) { details, credits, stills, videos, error ->
        SeasonDetailsScreenUiState(
            startRoute = startRoute,
            seasonDetails = details,
            aggregatedCredits = credits,
            videos = videos,
            episodeCount = details?.episodes?.count(),
            episodeStills = stills,
            error = error
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        SeasonDetailsScreenUiState.default
    )

    init {
        viewModelScope.launch {
            deviceLanguage.collectLatest { deviceLanguage ->
                launch {
                    getSeasonDetailsUseCase(
                        tvShowId = showId,
                        seasonNumber = seasonNumber,
                        deviceLanguage = deviceLanguage
                    ).onSuccess {
                        viewModelScope.launch {
                            seasonDetails.emit(
                                data
                            )
                        }
                    }.onError {
                        onError(
                            this
                        )
                    }.onException {
                        onException(
                            this
                        )
                    }
                }

                launch {
                    getSeasonCredits(
                        tvShowId = showId,
                        seasonNumber = seasonNumber,
                        deviceLanguage = deviceLanguage
                    )
                }

                launch {
                    getSeasonVideos(
                        tvShowId = showId,
                        seasonNumber = seasonNumber,
                        deviceLanguage = deviceLanguage
                    )
                }
            }
        }
    }

    fun getEpisodeStills(
        episodeNumber: Int
    ) {
        if (episodesStills.value.containsKey(episodeNumber)) {
            return
        }

        viewModelScope.launch {
            getEpisodeStillsUseCaseImpl(
                tvShowId = showId,
                seasonNumber = seasonNumber,
                episodeNumber = episodeNumber
            ).onSuccess {
                viewModelScope.launch {
                    episodesStills.collectLatest { current ->
                        val updatedStills = current.toMutableMap().apply {
                            put(
                                episodeNumber,
                                data ?: emptyList()
                            )
                        }
                        episodesStills.emit(
                            updatedStills
                        )
                    }
                }
            }.onError {
                onError(
                    this
                )
            }.onException {
                onException(
                    this
                )
            }
        }
    }

    private suspend fun getSeasonCredits(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto
    ) {
        getSeasonCreditsUseCase(
            tvShowId = tvShowId,
            seasonNumber = seasonNumber,
            deviceLanguage = deviceLanguage
        ).onSuccess {
            data?.let { credits ->
                viewModelScope.launch {
                    aggregatedCredits.emit(
                        credits
                    )
                }
            }
        }.onError {
            onError(
                this
            )
        }.onException {
            onException(
                this
            )
        }
    }

    private suspend fun getSeasonVideos(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguageDto
    ) {
        getSeasonsVideosUseCaseImpl(
            tvShowId = tvShowId,
            seasonNumber = seasonNumber,
            deviceLanguage = deviceLanguage
        ).onSuccess {
            viewModelScope.launch {
                videos.emit(
                    data ?: emptyList()
                )
            }
        }.onError {
            onError(
                this
            )
        }.onException {
            onException(
                this
            )
        }
    }
}