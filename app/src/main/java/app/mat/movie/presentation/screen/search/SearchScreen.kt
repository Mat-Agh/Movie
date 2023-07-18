package app.mat.movie.presentation.screen.search

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import app.mat.movie.R
import app.mat.movie.common.Parameter.Scan.Camera.SCAN_RESULT_KEY
import app.mat.movie.common.util.CaptureSpeechToText
import app.mat.movie.common.util.isNotEmpty
import app.mat.movie.data.local.entity.search.SearchQueryEntity
import app.mat.movie.data.remote.type.MediaType
import app.mat.movie.presentation.component.section.PresentableGridSection
import app.mat.movie.presentation.component.section.SearchGridSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.MovieDetailsScreen
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.ScannerScreen
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.ShowDetailsScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.screen.search.components.MovplaySearchEmptyState
import app.mat.movie.presentation.screen.search.components.QueryTextField
import app.mat.movie.presentation.theme.spacing
import java.util.Date

@Composable
fun AnimatedVisibilityScope.SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onQueryChanged: (query: String) -> Unit = viewModel::onQueryChange
    val onQueryCleared: () -> Unit = viewModel::onQueryClear
    val onAddSearchQuerySuggestions: (SearchQueryEntity) -> Unit = viewModel::addQuerySuggestion

    val onCameraClicked: () -> Unit = {
        navHostController.navigate(
            ScannerScreen.route
        )
    }

    val onResultClicked: (id: Int, type: MediaType) -> Unit = { id, type ->
        val route = when (type) {
            MediaType.Movie -> {
                MovieDetailsScreen.passArguments(
                    movieId = id,
                    startRoute = NavigationBarGraphScreen.SearchScreen.route
                )
            }

            MediaType.Tv -> {
                ShowDetailsScreen.passArguments(
                    showId = id,
                    startRoute = NavigationBarGraphScreen.SearchScreen.route
                )
            }

            else -> null
        }

        if (route != null) {
            val searchQuery = SearchQueryEntity(
                query = uiState.query.orEmpty(),
                lastUseDate = Date()
            )
            onAddSearchQuerySuggestions(
                searchQuery
            )

            navHostController.navigate(
                route
            )
        }
    }

    val onMovieClicked: (movieId: Int) -> Unit = { movieId: Int ->
        navHostController.navigate(
            MovieDetailsScreen.passArguments(
                movieId = movieId,
                startRoute = NavigationBarGraphScreen.SearchScreen.route
            )
        )
    }

    val onQuerySuggestionSelected: (String) -> Unit = viewModel::onQuerySuggestionSelected

    val scanResult = navHostController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>(
            SCAN_RESULT_KEY
        )?.observeAsState()

    scanResult?.value?.let { result ->
        viewModel.onQueryChange(
            result
        )
    }

    SearchScreenContent(
        uiState = uiState,
        onQueryChanged = onQueryChanged,
        onQueryCleared = onQueryCleared,
        onQuerySuggestionSelected = onQuerySuggestionSelected,
        onCameraClicked = onCameraClicked,
        onResultClicked = onResultClicked,
        onMovieClicked = onMovieClicked
    )
}

@Composable
fun SearchScreenContent(
    uiState: SearchScreenUIState,
    onQueryChanged: (query: String) -> Unit,
    onQueryCleared: () -> Unit,
    onResultClicked: (id: Int, type: MediaType) -> Unit,
    onCameraClicked: () -> Unit = {},
    onMovieClicked: (Int) -> Unit,
    onQuerySuggestionSelected: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val queryTextFieldFocusRequester = remember {
        FocusRequester()
    }
    val clearFocus = {
        focusManager.clearFocus(
            force = true
        )
    }
    val speechToTextLauncher = rememberLauncherForActivityResult(
        CaptureSpeechToText()
    ) { result ->
        if (result != null) {
            focusManager.clearFocus()
            onQueryChanged(
                result
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        QueryTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    MaterialTheme.spacing.extraSmall
                )
                .animateContentSize(),
            query = uiState.query,
            suggestions = uiState.suggestions,
            voiceSearchAvailable = uiState.searchOptionsState.voiceSearchAvailable,
            cameraSearchAvailable = uiState.searchOptionsState.cameraSearchAvailable,
            loading = uiState.queryLoading,
            showClearButton = uiState.searchState !is SearchState.EmptyQuery,
            focusRequester = queryTextFieldFocusRequester,
            info = {
                AnimatedVisibility(
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically(),
                    visible = uiState.searchState is SearchState.InsufficientQuery
                ) {
                    Text(
                        text = stringResource(
                            R.string.search_insufficient_query_length_info_text
                        )
                    )
                }
            },
            onKeyboardSearchClicked = {
                clearFocus()
            },
            onQueryChange = onQueryChanged,
            onQueryClear = {
                onQueryCleared()
                queryTextFieldFocusRequester.requestFocus()
            },
            onVoiceSearchClick = {
                speechToTextLauncher.launch(
                    null
                )
            },
            onCameraSearchClick = onCameraClicked,
            onSuggestionClick = { suggestion ->
                clearFocus()
                onQuerySuggestionSelected(
                    suggestion
                )
            }
        )

        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = uiState.resultState,
            label = ""
        ) { state ->
            when (state) {
                is ResultState.Default -> {
                    val popular = state.popular.collectAsLazyPagingItems()

                    if (popular.isNotEmpty()) {
                        PresentableGridSection(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = MaterialTheme.spacing.medium,
                                start = MaterialTheme.spacing.small,
                                end = MaterialTheme.spacing.small,
                                bottom = MaterialTheme.spacing.large
                            ),
                            state = popular,
                            onPresentableClick = onMovieClicked
                        )
                    }
                }

                is ResultState.Search -> {
                    val result = state.result.collectAsLazyPagingItems()

                    if (result.isNotEmpty()) {
                        SearchGridSection(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = MaterialTheme.spacing.medium,
                                start = MaterialTheme.spacing.small,
                                end = MaterialTheme.spacing.small,
                                bottom = MaterialTheme.spacing.large
                            ),
                            state = result,
                            onSearchResultClick = { id, mediaType ->
                                clearFocus()
                                onResultClicked(
                                    id,
                                    mediaType
                                )
                            }
                        )
                    } else {
                        MovplaySearchEmptyState(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = MaterialTheme.spacing.medium
                                )
                                .padding(
                                    top = MaterialTheme.spacing.extraLarge
                                ),
                            onEditButtonClicked = {
                                queryTextFieldFocusRequester.requestFocus()
                            }
                        )
                    }
                }
            }
        }
    }
}