package app.mat.movie.presentation.screen.favorite

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import app.mat.movie.common.type.FavoriteType
import app.mat.movie.common.util.isNotEmpty
import app.mat.movie.presentation.component.common.FavoriteEmptyState
import app.mat.movie.presentation.component.section.PresentableGridSection
import app.mat.movie.presentation.component.selector.FavoriteTypeSelector
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.MovieDetailsScreen
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.ShowDetailsScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.theme.spacing

@Composable
fun AnimatedVisibilityScope.FavoriteScreen(
    viewModel: FavoritesScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onFavoriteTypeSelected: (type: FavoriteType) -> Unit = viewModel::onFavoriteTypeSelected
    val onFavoriteClicked: (favoriteId: Int) -> Unit = { mediaId ->
        val route = when (uiState.selectedFavouriteType) {
            FavoriteType.Movie -> {
                MovieDetailsScreen.passArguments(
                    movieId = mediaId,
                    startRoute = NavigationBarGraphScreen.FavoriteScreen.route
                )
            }

            FavoriteType.TvShow -> {
                ShowDetailsScreen.passArguments(
                    showId = mediaId,
                    startRoute = NavigationBarGraphScreen.FavoriteScreen.route
                )
            }
        }

        navHostController.navigate(
            route
        )
    }

    val onNavigateToMoviesButtonClicked: () -> Unit = {
        navHostController.navigate(
            NavigationBarGraphScreen.MovieScreen.route
        ) {
            popUpTo(
                MovieDetailsScreen.route
            ) {
                inclusive = true
            }
        }
    }

    val onNavigateToTvShowButtonClicked: () -> Unit = {
        navHostController.navigate(
            NavigationBarGraphScreen.ShowScreen.route
        ) {
            popUpTo(
                ShowDetailsScreen.route
            ) {
                inclusive = true
            }
        }
    }

    FavoriteScreenContent(
        uiState = uiState,
        onFavoriteTypeSelected = onFavoriteTypeSelected,
        onFavoriteClicked = onFavoriteClicked,
        onNavigateToMoviesButtonClicked = onNavigateToMoviesButtonClicked,
        onNavigateToTvShowButtonClicked = onNavigateToTvShowButtonClicked
    )
}

@Composable
fun FavoriteScreenContent(
    uiState: FavoritesScreenUIState,
    onFavoriteTypeSelected: (type: FavoriteType) -> Unit,
    onFavoriteClicked: (favouriteId: Int) -> Unit,
    onNavigateToMoviesButtonClicked: () -> Unit,
    onNavigateToTvShowButtonClicked: () -> Unit
) {
    val favoritesLazyItems = uiState.favorites.collectAsLazyPagingItems()
    val notEmpty = favoritesLazyItems.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        FavoriteTypeSelector(
            selected = uiState.selectedFavouriteType,
            onSelected = onFavoriteTypeSelected
        )
        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = notEmpty,
            label = ""
        ) { notEmpty ->
            if (notEmpty) {
                PresentableGridSection(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = MaterialTheme.spacing.medium,
                        start = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.small,
                        bottom = MaterialTheme.spacing.large
                    ),
                    state = favoritesLazyItems,
                    showRefreshLoading = false,
                    onPresentableClick = onFavoriteClicked
                )
            } else {
                FavoriteEmptyState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = MaterialTheme.spacing.medium
                        )
                        .padding(
                            top = MaterialTheme.spacing.extraLarge
                        ),
                    type = uiState.selectedFavouriteType,
                    onButtonClick = when (uiState.selectedFavouriteType) {
                        FavoriteType.Movie -> onNavigateToMoviesButtonClicked
                        FavoriteType.TvShow -> onNavigateToTvShowButtonClicked
                    }
                )
            }
        }
    }
}