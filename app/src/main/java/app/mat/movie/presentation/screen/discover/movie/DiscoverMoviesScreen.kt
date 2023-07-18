package app.mat.movie.presentation.screen.discover.movie

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import app.mat.movie.R
import app.mat.movie.common.type.SortOrder
import app.mat.movie.common.type.SortType
import app.mat.movie.common.util.isEmpty
import app.mat.movie.presentation.component.button.FilterFloatingButton
import app.mat.movie.presentation.component.button.SortTypeDropDownButton
import app.mat.movie.presentation.component.common.BasicAppBar
import app.mat.movie.presentation.component.common.FilterEmptyState
import app.mat.movie.presentation.component.section.PresentableGridSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.screen.discover.components.FilterMoviesModalBottomSheetContent
import app.mat.movie.presentation.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun AnimatedVisibilityScope.DiscoverMoviesScreen(
    viewModel: DiscoverMoviesViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val onBackClicked: () -> Unit = {
        navHostController.navigateUp()
    }

    val onSortOrderChanged: (order: SortOrder) -> Unit = viewModel::onSortOrderChange

    val onSortTypeChanged: (type: SortType) -> Unit = viewModel::onSortTypeChange

    val onMovieClicked: (movieId: Int) -> Unit = { id ->
        navHostController.navigate(
            ApplicationGraphScreen.MovieDetailsScreen.passArguments(
                movieId = id,
                startRoute = NavigationBarGraphScreen.MovieScreen.route
            )
        )
    }

    val onSaveFilterClicked: (state: MovieFilterState) -> Unit = viewModel::onFilterStateChange

    DiscoverMoviesScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onSortOrderChanged = onSortOrderChanged,
        onSortTypeChanged = onSortTypeChanged,
        onMovieClicked = onMovieClicked,
        onSaveFilterClicked = onSaveFilterClicked
    )
}

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterialApi::class
)
@Composable
fun DiscoverMoviesScreenContent(
    uiState: DiscoverMoviesScreenUIState,
    onBackClicked: () -> Unit,
    onSortOrderChanged: (order: SortOrder) -> Unit,
    onSortTypeChanged: (type: SortType) -> Unit,
    onMovieClicked: (movieId: Int) -> Unit,
    onSaveFilterClicked: (state: MovieFilterState) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val gridState = rememberLazyGridState()

    val showFloatingButton = if (gridState.isScrollInProgress) {
        false
    } else {
        !sheetState.isVisible
    }

    val orderIconRotation by animateFloatAsState(
        targetValue = if (uiState.sortInfo.sortOrder == SortOrder.Desc) {
            0f
        } else 180f,
        label = ""
    )

    BackHandler(
        sheetState.isVisible
    ) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetContent = {
            FilterMoviesModalBottomSheetContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(
                        0.9f
                    )
                    .navigationBarsPadding(),
                sheetState = sheetState,
                filterState = uiState.filterState,
                onCloseClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                onSaveFilterClick = { filterState ->
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                    onSaveFilterClicked(
                        filterState
                    )
                }
            )
        },
        sheetBackgroundColor = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                BasicAppBar(
                    title = stringResource(
                        R.string.discover_movies_appbar_title
                    ),
                    action = {
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                modifier = Modifier.rotate(
                                    orderIconRotation
                                ),
                                onClick = {
                                    val order =
                                        if (uiState.sortInfo.sortOrder == SortOrder.Desc) {
                                            SortOrder.Asc
                                        } else {
                                            SortOrder.Desc
                                        }

                                    onSortOrderChanged(
                                        order
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDownward,
                                    contentDescription = "sort order",
                                )
                            }

                            SortTypeDropDownButton(
                                selectedType = uiState.sortInfo.sortType,
                                onTypeSelected = onSortTypeChanged
                            )
                        }
                    })

                Crossfade(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    targetState = !movies.isEmpty(),
                    label = ""
                ) { hasFilterResults ->
                    if (hasFilterResults) {
                        PresentableGridSection(
                            modifier = Modifier.fillMaxSize(),
                            gridState = gridState,
                            contentPadding = PaddingValues(
                                top = MaterialTheme.spacing.medium,
                                start = MaterialTheme.spacing.small,
                                end = MaterialTheme.spacing.small,
                                bottom = MaterialTheme.spacing.large
                            ),
                            state = movies,
                            onPresentableClick = onMovieClicked
                        )
                    } else {
                        FilterEmptyState(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = MaterialTheme.spacing.medium
                                )
                                .padding(
                                    top = MaterialTheme.spacing.extraLarge
                                ),
                            onFilterButtonClicked = {
                                coroutineScope.launch {
                                    sheetState.show()
                                }
                            }
                        )
                    }
                }
            }

            AnimatedVisibility(
                modifier = Modifier.align(
                    Alignment.BottomEnd
                ),
                visible = showFloatingButton,
                enter = fadeIn(
                    animationSpec = spring()
                ) + scaleIn(
                    animationSpec = spring(),
                    initialScale = 0.3f
                ),
                exit = fadeOut(
                    animationSpec = spring()
                ) + scaleOut(
                    animationSpec = spring(),
                    targetScale = 0.3f
                )
            ) {
                FilterFloatingButton(
                    modifier = Modifier
                        .padding(
                            MaterialTheme.spacing.medium
                        )
                        .navigationBarsPadding()
                        .imePadding(),
                    onClick = {
                        coroutineScope.launch {
                            if (sheetState.isVisible) {
                                sheetState.hide()
                            } else {
                                sheetState.show()
                            }
                        }
                    }
                )
            }
        }
    }
}