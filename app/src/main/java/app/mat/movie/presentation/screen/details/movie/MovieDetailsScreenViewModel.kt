package app.mat.movie.presentation.screen.details.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.onError
import app.mat.movie.common.onException
import app.mat.movie.common.onSuccess
import app.mat.movie.common.type.RelationType
import app.mat.movie.data.remote.dto.common.CreditsDto
import app.mat.movie.data.remote.dto.common.CrewMemberDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ImageDto
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.data.remote.dto.common.WatchProvidersDto
import app.mat.movie.data.remote.dto.movie.MovieCollectionDto
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.movie.AddRecentlyBrowsedMovieUseCase
import app.mat.movie.domain.userCase.movie.GetFavoriteMoviesIdsUseCase
import app.mat.movie.domain.userCase.movie.GetMovieBackdropsUseCase
import app.mat.movie.domain.userCase.movie.GetMovieCollectionUseCase
import app.mat.movie.domain.userCase.movie.GetMovieCreditUseCase
import app.mat.movie.domain.userCase.movie.GetMovieDetailsUseCase
import app.mat.movie.domain.userCase.movie.GetMovieExternalIdsUseCase
import app.mat.movie.domain.userCase.movie.GetMovieReviewsCountUseCase
import app.mat.movie.domain.userCase.movie.GetMovieVideosUseCase
import app.mat.movie.domain.userCase.movie.GetMovieWatchProvidersUseCase
import app.mat.movie.domain.userCase.movie.GetOtherDirectorMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetRelatedMoviesOfTypeUseCase
import app.mat.movie.domain.userCase.movie.LikeMovieUseCase
import app.mat.movie.domain.userCase.movie.UnlikeMovieUseCase
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.MovieDetailsScreenData
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    private val getRelatedMoviesUseCase: GetRelatedMoviesOfTypeUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieBackdropsUseCase: GetMovieBackdropsUseCase,
    private val getMovieExternalIdsUseCase: GetMovieExternalIdsUseCase,
    private val getMovieWatchProvidersUseCase: GetMovieWatchProvidersUseCase,
    private val getMovieReviewsCountUseCase: GetMovieReviewsCountUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditUseCase,
    private val getMoviesVideosUseCase: GetMovieVideosUseCase,
    private val getMovieCollectionUseCase: GetMovieCollectionUseCase,
    private val getOtherDirectorMoviesUseCase: GetOtherDirectorMoviesUseCase,
    getFavoriteMoviesIdsUseCaseImpl: GetFavoriteMoviesIdsUseCase,
    private val addRecentlyBrowsedMovieUseCase: AddRecentlyBrowsedMovieUseCase,
    private val unlikeMovieUseCase: UnlikeMovieUseCase,
    private val likeMovieUseCaseImpl: LikeMovieUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    //region Variables
    private val movieId: Int = savedStateHandle[MovieDetailsScreenData.MOVIE_DETAILS_REQUIRED_ARGUMENT_MOVIE_ID] ?: 0
    private val startRoute: String = savedStateHandle[MovieDetailsScreenData.MOVIE_DETAILS_REQUIRED_ARGUMENT_START_ROUTE] ?: NavigationBarGraphScreen.MovieScreen.route
    val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()
    private val favoritesMoviesIdsFlow: Flow<List<Int>> = getFavoriteMoviesIdsUseCaseImpl()
    private val watchAtTime: MutableStateFlow<Date?> = MutableStateFlow(
        null
    )
    private val _movieDetails: MutableStateFlow<MovieDetailsDto?> = MutableStateFlow(
        null
    )
    private val movieDetails: StateFlow<MovieDetailsDto?> =
        _movieDetails.onEach { movieDetails ->
            movieDetails?.let(
                addRecentlyBrowsedMovieUseCase::invoke
            )
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(
                10
            ), null
        )
    private val credits: MutableStateFlow<CreditsDto?> = MutableStateFlow(
        null
    )
    private val movieBackdrops: MutableStateFlow<List<ImageDto>> = MutableStateFlow(
        emptyList()
    )
    private val movieCollection: MutableStateFlow<MovieCollectionDto?> = MutableStateFlow(
        null
    )
    private val otherDirectorMovies: MutableStateFlow<DirectorMovies> = MutableStateFlow(
        DirectorMovies.default
    )
    private val watchProviders: MutableStateFlow<WatchProvidersDto?> = MutableStateFlow(
        null
    )
    private val videos: MutableStateFlow<List<VideoDto>?> = MutableStateFlow(
        null
    )
    private val reviewsCount: MutableStateFlow<Int> = MutableStateFlow(
        0
    )

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    private val isFavourite: Flow<Boolean> = favoritesMoviesIdsFlow.mapLatest { favouriteIds ->
        movieId in favouriteIds
    }
    private val externalIds: MutableStateFlow<List<ExternalIdsResource>?> = MutableStateFlow(
        null
    )
    private val additionalInfo: StateFlow<AdditionalMovieDetailsInfo> = combine(
        isFavourite, watchAtTime, watchProviders, credits, reviewsCount
    ) { isFavourite, watchAtTime, watchProviders, credits, reviewsCount ->
        AdditionalMovieDetailsInfo(
            isFavorite = isFavourite,
            watchAtTime = watchAtTime,
            watchProviders = watchProviders,
            credits = credits,
            reviewsCount = reviewsCount
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(
            10
        ),
        AdditionalMovieDetailsInfo.default
    )
    private val associatedMovies: StateFlow<AssociatedMovies> = combine(
        deviceLanguage, movieCollection, otherDirectorMovies
    ) { deviceLanguage, collection, otherDirectorMovies ->
        AssociatedMovies(
            collection = collection,
            similar = getRelatedMoviesUseCase(
                movieId = movieId,
                type = RelationType.Similar,
                deviceLanguage = deviceLanguage
            ).cachedIn(
                viewModelScope
            ),
            recommendations = getRelatedMoviesUseCase(
                movieId = movieId,
                type = RelationType.Recommended,
                deviceLanguage = deviceLanguage
            ).cachedIn(
                viewModelScope
            ),
            directorMovies = otherDirectorMovies
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(
            10
        ), AssociatedMovies.default
    )

    private val associatedContent: StateFlow<AssociatedContent> = combine(
        movieBackdrops, videos, externalIds
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

    val uiState: StateFlow<MovieDetailsScreenUIState> = combine(
        movieDetails, additionalInfo, associatedMovies, associatedContent, error
    ) { details, additionalInfo, associatedMovies, visualContent, error ->
        MovieDetailsScreenUIState(
            startRoute = startRoute,
            movieDetails = details,
            additionalMovieDetailsInfo = additionalInfo,
            associatedMovies = associatedMovies,
            associatedContent = visualContent,
            error = error
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        MovieDetailsScreenUIState.getDefault(
            startRoute
        )
    )
    //endregion Variables

    //region Initialization
    init {
        getMovieInfo()
    }
    //endregion

    //region Private Methods
    private fun getMovieInfo() = viewModelScope.launch {
        val movieId = movieId

        launch {
            getMovieBackdrops(movieId)
        }
        launch {
            getExternalIds(movieId)
        }
        launch {
            getMovieReview(movieId)
        }

        deviceLanguage.collectLatest { deviceLanguage ->
            launch {
                getMovieDetails(
                    movieId,
                    deviceLanguage
                )
            }
            launch {
                getWatchProviders(
                    movieId,
                    deviceLanguage
                )
            }
            launch {
                getMovieCredits(
                    movieId,
                    deviceLanguage
                )
            }
            launch {
                getMovieVideos(
                    movieId,
                    deviceLanguage
                )
            }
        }

        startRefreshingWatchAtTime()
    }

    private fun startRefreshingWatchAtTime() = viewModelScope.launch {
        _movieDetails.collectLatest { details ->
            while (isActive) {
                details?.runtime?.let { runtime ->
                    if (runtime > 0) {
                        runtime.minutes.toComponents { hours, minutes, _, _ ->
                            val time = Calendar.getInstance().apply {
                                time = Date()

                                add(
                                    Calendar.HOUR,
                                    hours.toInt()
                                )

                                add(
                                    Calendar.MINUTE,
                                    minutes
                                )
                            }.time

                            watchAtTime.emit(
                                time
                            )
                        }
                    }
                }

                delay(
                    10.seconds
                )
            }
        }
    }

    private suspend fun getMovieDetails(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getMovieDetailsUseCase(
        movieId = movieId,
        deviceLanguage = deviceLanguage
    ).onSuccess {
        viewModelScope.launch {
            val movieDetails = data
            _movieDetails.emit(movieDetails)

            data?.collection?.id?.let { collectionId ->
                getMovieCollection(
                    collectionId = collectionId,
                    deviceLanguage = deviceLanguage
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


    private suspend fun getMovieCredits(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getMovieCreditsUseCase(
        movieId = movieId,
        deviceLanguage = deviceLanguage
    ).onSuccess {
        viewModelScope.launch {
            credits.emit(data)

            val directors = data?.crew?.filter { member -> member.job == "Director" }
            val mainDirector = if (directors?.count() == 1) directors.first() else null

            if (mainDirector != null) {
                getOtherDirectorMovies(
                    mainDirector = mainDirector,
                    deviceLanguage = deviceLanguage
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

    private suspend fun getMovieBackdrops(
        movieId: Int
    ) = getMovieBackdropsUseCase(
        movieId
    ).onSuccess {
        viewModelScope.launch {
            movieBackdrops.emit(
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

    private suspend fun getMovieReview(
        movieId: Int
    ) = getMovieReviewsCountUseCase(
        movieId
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

    private suspend fun getMovieCollection(
        collectionId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getMovieCollectionUseCase(
        collectionId = collectionId,
        deviceLanguage = deviceLanguage
    ).onSuccess {
        viewModelScope.launch {
            movieCollection.emit(
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

    private suspend fun getWatchProviders(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getMovieWatchProvidersUseCase(
        movieId = movieId,
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
        movieId: Int
    ) = getMovieExternalIdsUseCase(
        movieId = movieId
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

    private suspend fun getMovieVideos(
        movieId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getMoviesVideosUseCase(
        movieId = movieId,
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

    private suspend fun getOtherDirectorMovies(
        mainDirector: CrewMemberDto,
        deviceLanguage: DeviceLanguageDto
    ) {
        val movies = getOtherDirectorMoviesUseCase(
            mainDirector = mainDirector,
            deviceLanguage = deviceLanguage
        ).cachedIn(
            viewModelScope
        )

        val directorMovies = DirectorMovies(
            directorName = mainDirector.name,
            movies = movies
        )

        otherDirectorMovies.emit(
            directorMovies
        )
    }
    //endregion Private Methods

    //region Public Methods
    fun onLikeClick(
        movieDetails: MovieDetailsDto
    ) = likeMovieUseCaseImpl(
        movieDetails
    )

    fun onUnlikeClick(
        movieDetails: MovieDetailsDto
    ) = unlikeMovieUseCase(
        movieDetails
    )
    //endregion Public Methods
}