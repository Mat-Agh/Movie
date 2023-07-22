package app.mat.movie.presentation.screen.browse.movie

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
import app.mat.movie.common.type.MovieType
import app.mat.movie.presentation.component.common.BasicAppBar
import app.mat.movie.presentation.component.dialog.InfoDialog
import app.mat.movie.presentation.component.section.PresentableGridSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen.MovieScreen
import app.mat.movie.presentation.theme.spacing

@Composable
fun AnimatedVisibilityScope.BrowseMoviesScreen(
    viewModel: BrowseMoviesViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBackClicked: () -> Unit = {
        navHostController.navigateUp()
    }
    val onClearDialogConfirmClick: () -> Unit = viewModel::onClearClicked

    val onMovieClicked: (movieId: Int) -> Unit = { movieId: Int ->
        navHostController.navigate(
            route = ApplicationGraphScreen.MovieDetailsScreen.passArguments(
                movieId = movieId,
                startRoute = MovieScreen.route
            )
        )
    }

    BrowseMoviesScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onClearDialogConfirmClicked = onClearDialogConfirmClick,
        onMovieClicked = onMovieClicked
    )
}

@Composable
fun BrowseMoviesScreenContent(
    uiState: BrowseMoviesScreenUIState,
    onBackClicked: () -> Unit,
    onClearDialogConfirmClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()
    val appbarTitle = when (uiState.selectedMovieType) {
        MovieType.NowPlaying -> stringResource(
            R.string.all_movies_now_playing_label
        )

        MovieType.Upcoming -> stringResource(
            R.string.all_movies_upcoming_label
        )

        MovieType.TopRated -> stringResource(
            R.string.all_movies_top_rated_label
        )

        MovieType.Favorite -> stringResource(
            R.string.all_movies_favourites_label,
            uiState.favoriteMoviesCount
        )

        MovieType.RecentlyBrowsed -> stringResource(
            R.string.all_movies_recently_browsed_label
        )

        MovieType.Trending -> stringResource(
            R.string.all_movies_trending_label
        )
    }
    val showClearButton = uiState.selectedMovieType == MovieType.RecentlyBrowsed && movies.itemSnapshotList.isNotEmpty()
    var showClearDialog by remember {
        mutableStateOf(false)
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
                R.string.clear_recent_movies_dialog_text
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
            title = appbarTitle,
            action = {
                IconButton(
                    onClick = { onBackClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
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
            state = movies,
            onPresentableClick = onMovieClicked
        )
    }
}