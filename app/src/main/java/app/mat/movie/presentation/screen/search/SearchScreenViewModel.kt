package app.mat.movie.presentation.screen.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.mat.movie.data.local.entity.search.SearchQueryEntity
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.Presentable
import app.mat.movie.domain.useCase.common.GetCameraAvailableUseCase
import app.mat.movie.domain.useCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.useCase.common.GetMediaMultiSearchUseCase
import app.mat.movie.domain.useCase.common.GetSpeechToTextAvailableUseCase
import app.mat.movie.domain.useCase.common.MediaAddSearchQueryUseCase
import app.mat.movie.domain.useCase.common.MediaSearchQueriesUseCase
import app.mat.movie.domain.useCase.movie.GetPopularMoviesUseCase
import app.mat.movie.presentation.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    getSpeechToTextAvailableUseCase: GetSpeechToTextAvailableUseCase,
    getCameraAvailableUseCase: GetCameraAvailableUseCase,
    private val mediaAddSearchQueryUseCase: MediaAddSearchQueryUseCase,
    private val mediaSearchQueriesUseCase: MediaSearchQueriesUseCase,
    private val getMediaMultiSearchUseCase: GetMediaMultiSearchUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : BaseViewModel() {
    //region Variables
    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()
    private val queryDelay = 500.milliseconds
    private val minQueryLength = 3

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    private val popularMovies: Flow<PagingData<Presentable>> =
        deviceLanguage.mapLatest { deviceLanguage ->
            getPopularMoviesUseCase(
                deviceLanguage
            )
        }.flattenMerge().cachedIn(
            viewModelScope
        )
    private val voiceSearchAvailable: Flow<Boolean> = getSpeechToTextAvailableUseCase()
    private val cameraSearchAvailable: Flow<Boolean> = getCameraAvailableUseCase()
    private val queryState: MutableStateFlow<QueryState> = MutableStateFlow(
        QueryState.default
    )
    private val suggestions: MutableStateFlow<List<String>> = MutableStateFlow(
        emptyList()
    )
    private val searchState: MutableStateFlow<SearchState> =
        MutableStateFlow(SearchState.EmptyQuery)
    private val resultState: MutableStateFlow<ResultState> =
        MutableStateFlow(
            ResultState.Default(
                popularMovies
            )
        )
    private val queryLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        false
    )

    private val searchOptionState: StateFlow<SearchOptionsState> = combine(
        voiceSearchAvailable, cameraSearchAvailable
    ) { voiceSearchAvailable, cameraSearchAvailable ->
        SearchOptionsState(
            voiceSearchAvailable = voiceSearchAvailable,
            cameraSearchAvailable = cameraSearchAvailable
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        SearchOptionsState.default
    )

    private var queryJob: Job? = null

    val uiState: StateFlow<SearchScreenUIState> = combine(
        searchOptionState, queryState, suggestions, searchState, resultState
    ) { searchOptionsState, queryState, suggestions, searchState, resultState ->
        SearchScreenUIState(
            searchOptionsState = searchOptionsState,
            query = queryState.query,
            suggestions = suggestions,
            searchState = searchState,
            resultState = resultState,
            queryLoading = queryState.loading
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        SearchScreenUIState.default
    )
    //endregion Variables

    //region Override Methods
    override fun onCleared() {
        super.onCleared()

        queryJob?.cancel()
    }
    //endregion Override Methods

    //region Public Methods
    fun onQueryChange(
        queryText: String
    ) {
        viewModelScope.launch {
            queryState.emit(
                queryState.value.copy(
                    query = queryText
                )
            )

            queryJob?.cancel()

            when {
                queryText.isBlank() -> {
                    searchState.emit(
                        SearchState.EmptyQuery
                    )
                    suggestions.emit(
                        emptyList()
                    )
                    resultState.emit(
                        ResultState.Default(
                            popularMovies
                        )
                    )
                }

                queryText.length < minQueryLength -> {
                    searchState.emit(
                        SearchState.InsufficientQuery
                    )
                    suggestions.emit(
                        emptyList()
                    )
                }

                else -> {
                    val querySuggestions = mediaSearchQueriesUseCase(
                        queryText
                    )
                    suggestions.emit(
                        querySuggestions
                    )

                    queryJob = createQueryJob(
                        queryText
                    ).apply {
                        start()
                    }
                }
            }
        }
    }

    fun onQueryClear() = onQueryChange(
        ""
    )

    fun onQuerySuggestionSelected(
        searchQuery: String
    ) {
        if (queryState.value.query != searchQuery) {
            onQueryChange(
                searchQuery
            )
        }
    }

    fun addQuerySuggestion(
        searchQuery: SearchQueryEntity
    ) = mediaAddSearchQueryUseCase(
        searchQuery
    )

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    private fun createQueryJob(
        query: String
    ): Job = viewModelScope.launch(
        Dispatchers.IO
    ) {
        try {
            delay(
                queryDelay
            )

            queryLoading.emit(
                true
            )

            val searchResults = deviceLanguage.mapLatest { deviceLanguage ->
                getMediaMultiSearchUseCase(
                    query = query,
                    deviceLanguage = deviceLanguage
                )
            }.flattenMerge().cachedIn(
                viewModelScope
            )

            searchState.emit(
                SearchState.ValidQuery
            )
            resultState.emit(
                ResultState.Search(
                    searchResults
                )
            )
        } catch (_: CancellationException) {

        } finally {
            withContext(
                NonCancellable
            ) {
                queryLoading.emit(
                    false
                )
            }
        }
    }
    //endregion Public Methods
}