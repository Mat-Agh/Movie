package app.mat.movie.presentation.screen.movie

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import app.mat.movie.R
import app.mat.movie.common.type.MovieType
import app.mat.movie.common.util.isAnyRefreshing
import app.mat.movie.common.util.isNotEmpty
import app.mat.movie.common.util.refreshAll
import app.mat.movie.common.util.toJsonString
import app.mat.movie.presentation.component.dialog.ExitDialog
import app.mat.movie.presentation.component.section.PresentableSection
import app.mat.movie.presentation.component.section.PresentableTopSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.*
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.theme.spacing
import app.mat.movie.presentation.view.MainViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AnimatedVisibilityScope.MovieScreen(
    mainViewModel: MainViewModel,
    viewModel: MovieScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(
        Unit
    ) {
        mainViewModel.sameBottomBarRoute.collectLatest { sameRoute ->
            if (sameRoute == NavigationBarGraphScreen.MovieScreen.route) {
                scrollState.animateScrollTo(
                    0
                )
            }
        }
    }

    val onMovieClicked = { movieId: Int ->
        navHostController.navigate(
            MovieDetailsScreen.passArguments(
                movieId = movieId,
                startRoute = NavigationBarGraphScreen.MovieScreen.route
            )
        )
    }

    val onBrowseMoviesClicked = { type: MovieType ->
        navHostController.navigate(
            BrowseMovieScreen.passArguments(
                movieType = type.toJsonString()
            )
        )
    }

    val onDiscoverMoviesClicked = {
        navHostController.navigate(
            DiscoverMovieScreen.route
        )
    }

    MoviesScreenContent(
        uiState = uiState,
        scrollState = scrollState,
        onMovieClicked = onMovieClicked,
        onBrowseMoviesClicked = onBrowseMoviesClicked,
        onDiscoverMoviesClicked = onDiscoverMoviesClicked
    )
}

@OptIn(
    ExperimentalMaterialApi::class
)
@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun MoviesScreenContent(
    uiState: MovieScreenUIState,
    scrollState: ScrollState,
    onMovieClicked: (movieId: Int) -> Unit,
    onBrowseMoviesClicked: (type: MovieType) -> Unit,
    onDiscoverMoviesClicked: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    val discoverLazyItems = uiState.moviesState.discover.collectAsLazyPagingItems()
    val upcomingLazyItems = uiState.moviesState.upcoming.collectAsLazyPagingItems()
    val topRatedLazyItems = uiState.moviesState.topRated.collectAsLazyPagingItems()
    val trendingLazyItems = uiState.moviesState.trending.collectAsLazyPagingItems()
    val nowPlayingLazyItems = uiState.moviesState.nowPlaying.collectAsLazyPagingItems()
    val favoritesLazyItems = uiState.favorites.collectAsLazyPagingItems()
    val recentlyBrowsedLazyItems = uiState.recentlyBrowsed.collectAsLazyPagingItems()

    var topSectionHeight: Float? by remember {
        mutableStateOf(
            null
        )
    }
    val appBarHeight = density.run { 56.dp.toPx() }
    val topSectionScrollLimitValue: Float? = topSectionHeight?.minus(
        appBarHeight
    )
    var showExitDialog by remember {
        mutableStateOf(
            false
        )
    }
    val dismissDialog = {
        showExitDialog = false
    }
    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        ExitDialog(
            onDismissRequest = dismissDialog,
            onCancelClicked = dismissDialog,
            onConfirmClicked = {
                val activity = (context as? Activity)
                activity?.finish()
            }
        )
    }

    val refreshAllPagingData = {
        listOf(
            discoverLazyItems,
            upcomingLazyItems,
            topRatedLazyItems,
            trendingLazyItems,
            nowPlayingLazyItems
        ).refreshAll()
    }

    val isRefreshing by derivedStateOf {
        listOf(
            discoverLazyItems,
            upcomingLazyItems,
            topRatedLazyItems,
            trendingLazyItems,
            nowPlayingLazyItems
        ).isAnyRefreshing()
    }

    val pullRefreshState = rememberPullRefreshState(
        isRefreshing,
        refreshAllPagingData
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(
                pullRefreshState
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium
            )
        ) {
            PresentableTopSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        topSectionHeight = coordinates.size.height.toFloat()
                    },
                title = stringResource(
                    R.string.now_playing_movies
                ),
                state = nowPlayingLazyItems,
                scrollState = scrollState,
                scrollValueLimit = topSectionScrollLimitValue,
                onPresentableClick = onMovieClicked,
                onMoreClick = {
                    onBrowseMoviesClicked(
                        MovieType.NowPlaying
                    )
                }
            )

            PresentableSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                title = stringResource(
                    R.string.explore_movies
                ),
                state = discoverLazyItems,
                onPresentableClick = onMovieClicked,
                onMoreClick = onDiscoverMoviesClicked
            )

            PresentableSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                title = stringResource(
                    R.string.upcoming_movies
                ),
                state = upcomingLazyItems,
                onPresentableClick = onMovieClicked,
                onMoreClick = {
                    onBrowseMoviesClicked(
                        MovieType.Upcoming
                    )
                }
            )

            PresentableSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                title = stringResource(
                    R.string.trending_movies
                ),
                state = trendingLazyItems,
                onPresentableClick = onMovieClicked,
                onMoreClick = {
                    onBrowseMoviesClicked(
                        MovieType.Trending
                    )
                }
            )

            PresentableSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                title = stringResource(
                    R.string.top_rated_movies
                ),
                state = topRatedLazyItems,
                onPresentableClick = onMovieClicked,
                onMoreClick = {
                    onBrowseMoviesClicked(
                        MovieType.TopRated
                    )
                }
            )

            if (favoritesLazyItems.isNotEmpty()) {
                PresentableSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    title = stringResource(
                        R.string.favourite_movies
                    ),
                    state = favoritesLazyItems,
                    onPresentableClick = onMovieClicked,
                    onMoreClick = {
                        onBrowseMoviesClicked(
                            MovieType.Favorite
                        )
                    }
                )
            }

            if (recentlyBrowsedLazyItems.isNotEmpty()) {
                PresentableSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    title = stringResource(
                        R.string.recently_browsed_movies
                    ),
                    state = recentlyBrowsedLazyItems,
                    onPresentableClick = onMovieClicked,
                    onMoreClick = {
                        onBrowseMoviesClicked(
                            MovieType.RecentlyBrowsed
                        )
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(
                    MaterialTheme.spacing.medium
                )
            )
        }

        PullRefreshIndicator(
            modifier = Modifier
                .align(
                    Alignment.TopCenter,
                ),
            refreshing = isRefreshing,
            state = pullRefreshState,
            scale = true,
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}