package app.mat.movie.presentation.screen.related.show

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import app.mat.movie.R
import app.mat.movie.common.type.RelationType
import app.mat.movie.presentation.component.common.BasicAppBar
import app.mat.movie.presentation.component.section.PresentableGridSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen
import app.mat.movie.presentation.theme.spacing

@Composable
fun AnimatedVisibilityScope.RelatedShowScreen(
    viewModel: RelatedShowViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val onBackButtonClicked: () -> Unit = { navHostController.navigateUp() }

    val onCloseClicked: () -> Unit = {
        navHostController.popBackStack(
            uiState.startRoute,
            inclusive = false
        )

    }

    val onShowClicked: (tvShowId: Int) -> Unit = { showId ->
        navHostController.navigate(
            ApplicationGraphScreen.ShowDetailsScreen.passArguments(
                showId = showId,
                startRoute = uiState.startRoute
            )
        )
    }


    RelatedTvSeriesScreenContent(
        uiState = uiState,
        onBackButtonClicked = onBackButtonClicked,
        onCloseClicked = onCloseClicked,
        onTvShowClicked = onShowClicked
    )
}

@Composable
fun RelatedTvSeriesScreenContent(
    uiState: RelatedShowScreenUiState,
    onBackButtonClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    onTvShowClicked: (tvShowId: Int) -> Unit
) {
    val tvShow = uiState.show.collectAsLazyPagingItems()

    val appbarTitle = stringResource(
        when (uiState.relationType) {
            RelationType.Similar -> R.string.related_tv_series_screen_similar_appbar_label
            RelationType.Recommended -> R.string.related_tv_series_screen_recommendations_appbar_label
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        BasicAppBar(
            title = appbarTitle,
            action = {
                IconButton(
                    onClick = onBackButtonClicked
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "go back",
                    )
                }
            },
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