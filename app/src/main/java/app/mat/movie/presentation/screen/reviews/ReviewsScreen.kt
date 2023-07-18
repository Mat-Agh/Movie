package app.mat.movie.presentation.screen.reviews

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import app.mat.movie.presentation.component.common.BasicAppBar
import app.mat.movie.presentation.component.list.ReviewsList
import app.mat.movie.presentation.theme.spacing

@Composable
fun AnimatedVisibilityScope.ReviewsScreen(
    viewModel: ReviewsViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBackClicked: () -> Unit = {
        navHostController.navigateUp()
    }
    val onCloseClicked: () -> Unit = {
        navHostController.popBackStack(
            uiState.startRoute,
            inclusive = false
        )
    }

    ReviewsScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onCloseClicked = onCloseClicked
    )
}

@Composable
fun ReviewsScreenContent(
    uiState: ReviewsScreenUiState,
    onBackClicked: () -> Unit,
    onCloseClicked: () -> Unit
) {
    val reviewsLazyItems = uiState.reviews.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        BasicAppBar(
            title = stringResource(
                R.string.reviews_screen_appbar_title
            ),
            action = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "go back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
        )

        ReviewsList(
            reviews = reviewsLazyItems,
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(
                top = MaterialTheme.spacing.medium,
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                bottom = MaterialTheme.spacing.large
            ),
            arrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.large
            )
        )
    }
}