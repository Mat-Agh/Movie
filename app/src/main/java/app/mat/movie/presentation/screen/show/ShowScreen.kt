package app.mat.movie.presentation.screen.show

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import app.mat.movie.R
import app.mat.movie.common.type.ShowType
import app.mat.movie.common.util.isAnyRefreshing
import app.mat.movie.common.util.isNotEmpty
import app.mat.movie.common.util.refreshAll
import app.mat.movie.common.util.toJsonString
import app.mat.movie.presentation.component.section.PresentableSection
import app.mat.movie.presentation.component.section.PresentableTopSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.*
import app.mat.movie.presentation.navigation.screen.MainGraphScreen.*
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.theme.spacing
import app.mat.movie.presentation.view.MainViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AnimatedVisibilityScope.ShowScreen(
    mainViewModel: MainViewModel,
    viewModel: ShowScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(
        Unit
    ) {
        mainViewModel.sameBottomBarRoute.collectLatest { sameRoute ->
            if (sameRoute == NavigationBarGraphScreen.ShowScreen.route) {
                scrollState.animateScrollTo(
                    0
                )
            }
        }
    }

    val onShowClicked: (showId: Int) -> Unit = { showId: Int ->
        navHostController.navigate(
            ShowDetailsScreen.passArguments(
                showId = showId,
                startRoute = NavigationBarGraphScreen.ShowScreen.route
            )
        )
    }

    val onBrowseShowClicked: (ShowType) -> Unit = { type ->
        navHostController.navigate(
            BrowseShowsScreen.passArguments(
                showType = type.toJsonString()
            )
        )
    }

    val onDiscoverShowClicked: () -> Unit = {
        navHostController.navigate(
            DiscoverShowsScreen.route
        )
    }

    ShowsScreenContent(
        uiState = uiState,
        scrollState = scrollState,
        onTvShowClicked = onShowClicked,
        onBrowseTvShowClicked = onBrowseShowClicked,
        onDiscoverTvShowClicked = onDiscoverShowClicked
    )
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun ShowsScreenContent(
    uiState: TvShowScreenUIState,
    scrollState: ScrollState,
    onTvShowClicked: (tvShowId: Int) -> Unit,
    onBrowseTvShowClicked: (type: ShowType) -> Unit,
    onDiscoverTvShowClicked: () -> Unit
) {
    val density = LocalDensity.current

    val topRatedLazyItems = uiState.showsState.topRated.collectAsLazyPagingItems()
    val discoverLazyItems = uiState.showsState.discover.collectAsLazyPagingItems()
    val onTheAirLazyItems = uiState.showsState.onTheAir.collectAsLazyPagingItems()
    val trendingLazyItems = uiState.showsState.trending.collectAsLazyPagingItems()
    val airingTodayLazyItems = uiState.showsState.airingToday.collectAsLazyPagingItems()
    val favoritesLazyItems = uiState.favorites.collectAsLazyPagingItems()
    val recentlyBrowsedLazyItems = uiState.recentlyBrowsed.collectAsLazyPagingItems()

    var topSectionHeight: Float? by remember {
        mutableStateOf(
            null
        )
    }
    val appBarHeight = density.run {
        56.dp.toPx()
    }
    val topSectionScrollLimitValue: Float? = topSectionHeight?.minus(
        appBarHeight
    )

    val isRefreshing by derivedStateOf {
        listOf(
            topRatedLazyItems,
            discoverLazyItems,
            onTheAirLazyItems,
            trendingLazyItems,
            airingTodayLazyItems,
        ).isAnyRefreshing()
    }

    val refreshAllPagingData = {
        listOf(
            topRatedLazyItems,
            discoverLazyItems,
            onTheAirLazyItems,
            trendingLazyItems,
            airingTodayLazyItems
        ).refreshAll()
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
                    R.string.now_airing_tv_series
                ),
                state = onTheAirLazyItems,
                scrollState = scrollState,
                scrollValueLimit = topSectionScrollLimitValue,
                onPresentableClick = onTvShowClicked,
                onMoreClick = {
                    onBrowseTvShowClicked(
                        ShowType.OnTheAir
                    )
                }
            )

            PresentableSection(
                title = stringResource(
                    R.string.explore_tv_series
                ),
                state = discoverLazyItems,
                onPresentableClick = onTvShowClicked,
                onMoreClick = onDiscoverTvShowClicked
            )

            PresentableSection(
                title = stringResource(
                    R.string.top_rated_tv_series
                ),
                state = topRatedLazyItems,
                onPresentableClick = onTvShowClicked,
                onMoreClick = {
                    onBrowseTvShowClicked(
                        ShowType.TopRated
                    )
                }
            )

            PresentableSection(
                title = stringResource(
                    R.string.trending_tv_series
                ),
                state = trendingLazyItems,
                onPresentableClick = onTvShowClicked,
                onMoreClick = {
                    onBrowseTvShowClicked(
                        ShowType.Trending
                    )
                }
            )

            PresentableSection(
                title = stringResource(
                    R.string.today_airing_tv_series
                ),
                state = airingTodayLazyItems,
                onPresentableClick = onTvShowClicked,
                onMoreClick = {
                    onBrowseTvShowClicked(
                        ShowType.AiringToday
                    )
                }
            )

            if (favoritesLazyItems.isNotEmpty()) {
                PresentableSection(
                    title = stringResource(
                        R.string.favourites_tv_series
                    ),
                    state = favoritesLazyItems,
                    onPresentableClick = onTvShowClicked,
                    onMoreClick = {
                        onBrowseTvShowClicked(
                            ShowType.Favorite
                        )
                    }
                )
            }

            if (recentlyBrowsedLazyItems.isNotEmpty()) {
                PresentableSection(
                    title = stringResource(
                        R.string.recently_browsed_tv_series
                    ),
                    state = recentlyBrowsedLazyItems,
                    onPresentableClick = onTvShowClicked,
                    onMoreClick = {
                        onBrowseTvShowClicked(
                            ShowType.RecentlyBrowsed
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
                .statusBarsPadding()
                .align(
                    Alignment.TopCenter
                ),
            refreshing = isRefreshing,
            state = pullRefreshState,
            scale = true,
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}