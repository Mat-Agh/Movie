package app.mat.movie.presentation.screen.browse.show

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import app.mat.movie.R
import app.mat.movie.common.type.ShowType
import app.mat.movie.presentation.component.common.BasicAppBar
import app.mat.movie.presentation.component.dialog.InfoDialog
import app.mat.movie.presentation.component.section.PresentableGridSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.ShowDetailsScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen.ShowScreen
import app.mat.movie.presentation.theme.spacing

@Composable
fun AnimatedVisibilityScope.BrowseShowsScreen(
    viewModel: BrowseShowsViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val onBackClicked: () -> Unit = {
        navHostController.navigateUp()
    }

    val onClearDialogConfirmClicked: () -> Unit = viewModel::onClearClicked

    val onShowClicked: (showId: Int) -> Unit = { showId: Int ->
        navHostController.navigate(
            ShowDetailsScreen.passArguments(
                showId = showId,
                startRoute = ShowScreen.route
            )
        )
    }

    BrowseTvShowScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onClearDialogConfirmClicked = onClearDialogConfirmClicked,
        onTvShowClicked = onShowClicked
    )
}

@Composable
fun BrowseTvShowScreenContent(
    uiState: BrowseTvShowsScreenUIState,
    onBackClicked: () -> Unit,
    onClearDialogConfirmClicked: () -> Unit,
    onTvShowClicked: (tvShowId: Int) -> Unit
) {
    val tvShow = uiState.tvShow.collectAsLazyPagingItems()
    val appbarTitle = when (uiState.selectedTvShowType) {
        ShowType.OnTheAir -> stringResource(
            R.string.all_tv_series_on_the_air_label
        )

        ShowType.TopRated -> stringResource(
            R.string.all_tv_series_top_rated_label
        )

        ShowType.AiringToday -> stringResource(
            R.string.all_tv_series_airing_today_label
        )

        ShowType.Favorite -> stringResource(
            R.string.all_tv_series_favourites_label,
            uiState.favoriteTvShowsCount
        )

        ShowType.RecentlyBrowsed -> stringResource(
            R.string.all_tv_series_recently_browsed_label
        )

        ShowType.Trending -> stringResource(
            R.string.all_tv_series_trending_label
        )
    }
    val showClearButton = uiState.selectedTvShowType == ShowType.RecentlyBrowsed && tvShow.itemSnapshotList.isNotEmpty()

    var showClearDialog by remember {
        mutableStateOf(
            false
        )
    }

    val showDialog = {
        showClearDialog = true
    }

    val dismissDialog = {
        showClearDialog = false
    }

    if (showClearDialog) {
        InfoDialog(
            infoText = stringResource(
                R.string.clear_recent_tv_series_dialog_text
            ),
            onDismissRequest = dismissDialog,
            onCancelClick = dismissDialog,
            onConfirmClick = {
                onClearDialogConfirmClicked()
                dismissDialog()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BasicAppBar(
            title = appbarTitle, action = {
                IconButton(
                    onClick = onBackClicked
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "go back",
                    )
                }
            },
            trailing = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        onClick = showDialog
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "clear recent",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        )

        PresentableGridSection(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(
                top = MaterialTheme.spacing.medium,
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.large
            ),
            state = tvShow,
            onPresentableClick = onTvShowClicked
        )
    }
}