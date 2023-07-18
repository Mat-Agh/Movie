package app.mat.movie.presentation.screen.details.show

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.onError
import app.mat.movie.common.onException
import app.mat.movie.common.onSuccess
import app.mat.movie.common.type.RelationType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ImageDto
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.data.remote.dto.common.WatchProvidersDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.show.AddRecentlyBrowsedShowUseCase
import app.mat.movie.domain.userCase.show.GetFavoriteShowIdsUseCase
import app.mat.movie.domain.userCase.show.GetNextEpisodeDaysRemainingUseCase
import app.mat.movie.domain.userCase.show.GetRelatedShowsOfTypeUseCase
import app.mat.movie.domain.userCase.show.GetShowDetailsUseCase
import app.mat.movie.domain.userCase.show.GetShowExternalIdsUseCase
import app.mat.movie.domain.userCase.show.GetShowImagesUseCase
import app.mat.movie.domain.userCase.show.GetShowReviewsCountUseCase
import app.mat.movie.domain.userCase.show.GetShowVideosUseCase
import app.mat.movie.domain.userCase.show.GetShowWatchProvidersUseCase
import app.mat.movie.domain.userCase.show.LikeShowUseCase
import app.mat.movie.domain.userCase.show.UnlikeShowUseCase
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.ShowDetailsScreenData
import app.mat.movie.presentation.navigation.screen.MainGraphScreen.*
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.screen.details.movie.AssociatedContent
import app.mat.movie.presentation.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsScreenViewModel @Inject constructor(
    getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    private val addRecentlyBrowsedTvShowUseCase: AddRecentlyBrowsedShowUseCase,
    getFavoriteTvShowIdsUseCase: GetFavoriteShowIdsUseCase,
    private val getRelatedTvShowsOfTypeUseCase: GetRelatedShowsOfTypeUseCase,
    private val getTvShowDetailsUseCase: GetShowDetailsUseCase,
    private val getNextEpisodeDaysRemainingUseCase: GetNextEpisodeDaysRemainingUseCase,
    private val getTvShowExternalIdsUseCase: GetShowExternalIdsUseCase,
    private val getTvShowImagesUseCase: GetShowImagesUseCase,
    private val getTvShowReviewsCountUseCase: GetShowReviewsCountUseCase,
    private val getTvShowVideosUseCase: GetShowVideosUseCase,
    private val getTvShowWatchProvidersUseCase: GetShowWatchProvidersUseCase,
    private val likeTvShowUseCase: LikeShowUseCase,
    private val unlikeTvShowUseCase: UnlikeShowUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    //region Variables
    private val showId: Int = savedStateHandle[ShowDetailsScreenData.SHOW_DETAILS_REQUIRED_ARGUMENT_SHOW_ID] ?: 0
    private val startRoute: String = savedStateHandle[ShowDetailsScreenData.SHOW_DETAILS_REQUIRED_ARGUMENT_START_ROUTE] ?: NavigationBarGraphScreen.ShowScreen.route
    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()
    private val favoriteTvShowIds: Flow<List<Int>> = getFavoriteTvShowIdsUseCase()
    private val _tvShowDetails: MutableStateFlow<ShowDetailsDto?> = MutableStateFlow(null)
    private val tvShowDetails: StateFlow<ShowDetailsDto?> = _tvShowDetails.onEach { showDetails ->
        showDetails?.let(
            addRecentlyBrowsedTvShowUseCase::invoke
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(
            10
        ), null
    )
    private val tvShowBackdrops: MutableStateFlow<List<ImageDto>> = MutableStateFlow(
        emptyList()
    )
    private val videos: MutableStateFlow<List<VideoDto>?> = MutableStateFlow(
        null
    )
    private val nextEpisodeDaysRemaining: MutableStateFlow<Long?> = MutableStateFlow(
        null
    )
    private val watchProviders: MutableStateFlow<WatchProvidersDto?> = MutableStateFlow(
        null
    )
    private val reviewsCount: MutableStateFlow<Int> = MutableStateFlow(
        0
    )

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    private val isFavorite: StateFlow<Boolean> = favoriteTvShowIds.mapLatest { favoriteIds ->
        showId in favoriteIds
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(
            10
        ),
        false
    )
    private val externalIds: MutableStateFlow<List<ExternalIdsResource>?> = MutableStateFlow(
        null
    )
    private val additionalInfo: StateFlow<AdditionalShowDetailsInfo> = combine(
        isFavorite,
        nextEpisodeDaysRemaining,
        watchProviders,
        reviewsCount
    ) { isFavorite, nextEpisodeDaysRemaining, watchProviders, reviewsCount ->
        AdditionalShowDetailsInfo(
            isFavorite = isFavorite,
            nextEpisodeRemainingDays = nextEpisodeDaysRemaining,
            watchProviders = watchProviders,
            reviewsCount = reviewsCount
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(
            10
        ),
        AdditionalShowDetailsInfo.default
    )

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    private val associatedTvShow: StateFlow<AssociatedTvShow> =
        deviceLanguage.mapLatest { deviceLanguage ->
            AssociatedTvShow(
                similar = getRelatedTvShowsOfTypeUseCase(
                    tvShowId = showId,
                    relationType = RelationType.Similar,
                    deviceLanguage = deviceLanguage
                ).cachedIn(
                    viewModelScope
                ),
                recommendations = getRelatedTvShowsOfTypeUseCase(
                    tvShowId = showId,
                    relationType = RelationType.Recommended,
                    deviceLanguage = deviceLanguage
                ).cachedIn(
                    viewModelScope
                )
            )
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(
                10
            ), AssociatedTvShow.default
        )

    private val associatedContent: StateFlow<AssociatedContent> = combine(
        tvShowBackdrops,
        videos,
        externalIds
    ) { backdrops, videos, externalIds ->
        AssociatedContent(
            backdrops = backdrops,
            videos = videos,
            externalIds = externalIds
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(
            10
        ), AssociatedContent.default
    )
    val uiState: StateFlow<ShowDetailsScreenUIState> = combine(
        tvShowDetails,
        additionalInfo,
        associatedTvShow,
        associatedContent,
        error
    ) { details, additionalInfo, associatedTvSeries, visualContent, error ->
        ShowDetailsScreenUIState(
            startRoute = startRoute,
            tvShowDetails = details,
            additionalTvShowDetailsInfo = additionalInfo,
            associatedTvShow = associatedTvSeries,
            associatedContent = visualContent,
            error = error
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ShowDetailsScreenUIState.getDefault(
            startRoute
        )
    )
    //endregion Variables

    //region Initialization
    init {
        getTvShowsInfo()
    }
    //endregion Initialization

    //region Private Methods
    private fun getTvShowsInfo() {
        val showId = showId

        viewModelScope.launch {
            launch {
                getTvShowImages(showId)
            }

            launch {
                getExternalIds(showId)
            }

            launch {
                getTvShowReviewsCount(showId)
            }

            deviceLanguage.collectLatest { deviceLanguage ->
                launch {
                    getTvShowDetails(
                        tvShowId = showId,
                        deviceLanguage = deviceLanguage
                    )
                }

                launch {
                    getWatchProviders(
                        tvShowId = showId,
                        deviceLanguage = deviceLanguage
                    )
                }

                launch {
                    getTvShowVideos(
                        showId = showId,
                        deviceLanguage = deviceLanguage
                    )
                }
            }
        }
    }

    private suspend fun getTvShowDetails(
        tvShowId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getTvShowDetailsUseCase(
        tvShowId = tvShowId,
        deviceLanguage = deviceLanguage
    ).onSuccess {
        viewModelScope.launch {
            val showDetails = data
            _tvShowDetails.emit(
                showDetails
            )

            showDetails?.nextEpisodeToAir?.airDate?.let { date ->
                val daysRemaining = getNextEpisodeDaysRemainingUseCase(
                    date
                )
                nextEpisodeDaysRemaining.emit(
                    daysRemaining
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

    private suspend fun getTvShowImages(
        tvShowId: Int
    ) = getTvShowImagesUseCase(
        tvShowId
    ).onSuccess {
        viewModelScope.launch {
            tvShowBackdrops.emit(data ?: emptyList())
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

    private suspend fun getTvShowReviewsCount(
        tvShowId: Int
    ) = getTvShowReviewsCountUseCase(
        tvShowId
    ).onSuccess {
        viewModelScope.launch {
            reviewsCount.emit(
                data ?: 0
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

    private suspend fun getWatchProviders(
        tvShowId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getTvShowWatchProvidersUseCase(
        tvShowId = tvShowId,
        deviceLanguage = deviceLanguage
    ).onSuccess {
        viewModelScope.launch {
            watchProviders.emit(
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

    private suspend fun getExternalIds(
        tvShowId: Int
    ) = getTvShowExternalIdsUseCase(
        tvShowId = tvShowId
    ).onSuccess {
        viewModelScope.launch {
            externalIds.emit(
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

    private suspend fun getTvShowVideos(
        showId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getTvShowVideosUseCase(
        showId = showId,
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
    //endregion Private Methods

    //region Public Methods
    fun onLikeClick(
        showDetails: ShowDetailsDto
    ) = likeTvShowUseCase(
        showDetails
    )

    fun onUnlikeClick(
        seriesDetails: ShowDetailsDto
    ) = unlikeTvShowUseCase(
        seriesDetails
    )
    //endregion Public Methods
}