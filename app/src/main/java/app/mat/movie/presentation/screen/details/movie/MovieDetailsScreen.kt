package app.mat.movie.presentation.screen.details.movie

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import app.mat.movie.common.type.RelationType
import app.mat.movie.common.util.ifNotNullAndEmpty
import app.mat.movie.common.util.isNotEmpty
import app.mat.movie.common.util.openExternalId
import app.mat.movie.common.util.openVideo
import app.mat.movie.common.util.shareImdb
import app.mat.movie.common.util.toJsonString
import app.mat.movie.data.remote.dto.common.ShareDetailsModel
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.data.remote.type.MediaType
import app.mat.movie.presentation.component.button.BackButton
import app.mat.movie.presentation.component.button.LikeButton
import app.mat.movie.presentation.component.common.AnimatedContentContainer
import app.mat.movie.presentation.component.common.DetailsAppBar
import app.mat.movie.presentation.component.dialog.ErrorDialog
import app.mat.movie.presentation.component.section.ExternalIdsSection
import app.mat.movie.presentation.component.section.MemberSection
import app.mat.movie.presentation.component.section.PresentableDetailsTopSection
import app.mat.movie.presentation.component.section.PresentableListSection
import app.mat.movie.presentation.component.section.PresentableSection
import app.mat.movie.presentation.component.section.ReviewSection
import app.mat.movie.presentation.component.section.VideosSection
import app.mat.movie.presentation.component.section.WatchProvidersSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.*
import app.mat.movie.presentation.screen.details.component.MovplayMovieDetailsInfoSection
import app.mat.movie.presentation.screen.details.component.MovplayMovieDetailsTopContent
import app.mat.movie.presentation.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun AnimatedVisibilityScope.MovieDetailsScreen(
    viewModel: MovieDetailsScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val onBackClicked: () -> Unit = {
        navHostController.navigateUp()
    }

    val onFavouriteClicked: (details: MovieDetailsDto) -> Unit = { details ->
        if (uiState.additionalMovieDetailsInfo.isFavorite) {
            viewModel.onUnlikeClick(
                details
            )
        } else {
            viewModel.onLikeClick(
                details
            )
        }
    }

    val onCloseClicked: () -> Unit = {
        navHostController.popBackStack(
            route = uiState.startRoute,
            inclusive = false
        )
    }

    val onExternalIdClicked = { id: ExternalIdsResource ->
        openExternalId(
            context = context,
            externalId = id
        )
    }

    val onShareClicked = { details: ShareDetailsModel ->
        shareImdb(
            context = context,
            details = details
        )
    }

    val onVideoClicked = { video: VideoDto ->
        openVideo(
            context = context,
            video = video
        )
    }

    val onMemberClicked: (personId: Int) -> Unit = { personId: Int ->
        navHostController.navigate(
            PersonDetailsScreen.passArguments(
                personId = personId,
                startRoute = MovieDetailsScreen.route
            )
        )
    }

    val onMovieClicked: (movieId: Int) -> Unit = { movieId: Int ->
        navHostController.navigate(
            MovieDetailsScreen.passArguments(
                movieId = movieId,
                startRoute = MovieDetailsScreen.route
            )
        )
    }

    val onReviewsClicked: () -> Unit = {
        val movieId = uiState.movieDetails?.id ?: 0

        navHostController.navigate(
            ReviewScreen.passArguments(
                mediaId = movieId,
                mediaType = MediaType.Movie.toJsonString(),
                startRoute = MovieDetailsScreen.route
            )
        )
    }

    val onSimilarMoreClicked = {
        val movieId = uiState.movieDetails?.id ?: 0

        navHostController.navigate(
            RelatedMoviesScreen.passArguments(
                movieId = movieId,
                relationType = RelationType.Similar.toJsonString(),
                startRoute = MovieDetailsScreen.route
            )
        )
    }

    val onRecommendationsMoreClicked = {
        val movieId = uiState.movieDetails?.id ?: 0

        navHostController.navigate(
            RelatedMoviesScreen.passArguments(
                movieId = movieId,
                relationType = RelationType.Recommended.toJsonString(),
                startRoute = MovieDetailsScreen.route
            )
        )
    }

    MovieDetailsScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onExternalIdClicked = onExternalIdClicked,
        onShareClicked = onShareClicked,
        onVideoClicked = onVideoClicked,
        onFavouriteClicked = onFavouriteClicked,
        onCloseClicked = onCloseClicked,
        onMemberClicked = onMemberClicked,
        onMovieClicked = onMovieClicked,
        onSimilarMoreClicked = onSimilarMoreClicked,
        onRecommendationsMoreClicked = onRecommendationsMoreClicked,
        onReviewsClicked = onReviewsClicked
    )
}

@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun MovieDetailsScreenContent(
    uiState: MovieDetailsScreenUIState,
    onBackClicked: () -> Unit,
    onExternalIdClicked: (id: ExternalIdsResource) -> Unit,
    onShareClicked: (details: ShareDetailsModel) -> Unit,
    onVideoClicked: (video: VideoDto) -> Unit,
    onFavouriteClicked: (details: MovieDetailsDto) -> Unit,
    onCloseClicked: () -> Unit,
    onMemberClicked: (personId: Int) -> Unit,
    onMovieClicked: (movieId: Int) -> Unit,
    onSimilarMoreClicked: () -> Unit,
    onRecommendationsMoreClicked: () -> Unit,
    onReviewsClicked: () -> Unit
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val similarMoviesState = uiState.associatedMovies.similar.collectAsLazyPagingItems()
    val moviesRecommendationState = uiState.associatedMovies.recommendations.collectAsLazyPagingItems()
    val otherDirectorMoviesState = uiState.associatedMovies.directorMovies.movies.collectAsLazyPagingItems()
    val scrollState = rememberScrollState()
    val scrollToStart = {
        coroutineScope.launch {
            scrollState.animateScrollTo(
                0
            )
        }
    }
    val imdbExternalId by derivedStateOf {
        uiState.associatedContent.externalIds?.filterIsInstance<ExternalIdsResource.Imdb>()?.firstOrNull()
    }
    var showErrorDialog by remember {
        mutableStateOf(
            false
        )
    }
    var topSectionHeight: Float? by remember {
        mutableStateOf(
            null
        )
    }
    val appbarHeight = density.run { 56.dp.toPx() }
    val topSectionScrollLimitValue: Float? = topSectionHeight?.minus(
        appbarHeight
    )

    LaunchedEffect(
        uiState.error
    ) {
        showErrorDialog = uiState.error != null
    }

    BackHandler(
        showErrorDialog
    ) {
        showErrorDialog = false
    }

    if (showErrorDialog) {
        ErrorDialog(
            onDismissRequest = {
                showErrorDialog = false
            },
            onConfirmClick = {
                showErrorDialog = false
            }
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    scrollState
                ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium
            )
        ) {
            PresentableDetailsTopSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        topSectionHeight = coordinates.size.height.toFloat()
                    },
                presentable = uiState.movieDetails,
                backdrops = uiState.associatedContent.backdrops,
                scrollState = scrollState,
                scrollValueLimit = topSectionScrollLimitValue
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(
                            MaterialTheme.spacing.small
                        )
                ) {
                    MovplayMovieDetailsTopContent(
                        modifier = Modifier.fillMaxWidth(),
                        movieDetails = uiState.movieDetails
                    )
                }
                Spacer(
                    modifier = Modifier.weight(
                        1f
                    )
                )
                Crossfade(
                    modifier = Modifier.fillMaxWidth(),
                    targetState = uiState.associatedContent.externalIds,
                    label = ""
                ) { ids ->
                    if (ids != null) {
                        ExternalIdsSection(
                            modifier = Modifier.fillMaxWidth(),
                            externalIds = ids,
                            onExternalIdClick = onExternalIdClicked
                        )
                    }
                }
            }

            MovplayMovieDetailsInfoSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium
                    )
                    .animateContentSize(),
                movieDetails = uiState.movieDetails,
                watchAtTime = uiState.additionalMovieDetailsInfo.watchAtTime,
                imdbExternalId = imdbExternalId,
                onShareClicked = onShareClicked
            )

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.additionalMovieDetailsInfo.watchProviders != null
            ) {
                if (uiState.additionalMovieDetailsInfo.watchProviders != null) {
                    WatchProvidersSection(
                        modifier = Modifier.fillMaxWidth(),
                        watchProviders = uiState.additionalMovieDetailsInfo.watchProviders,
                        title = stringResource(
                            R.string.available_at
                        )
                    )
                }
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.additionalMovieDetailsInfo.credits?.cast.isNullOrEmpty()
            ) {
                uiState.additionalMovieDetailsInfo.credits?.cast?.ifNotNullAndEmpty { members ->
                    MemberSection(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(
                            R.string.movie_details_cast
                        ),
                        members = members,
                        contentPadding = PaddingValues(
                            horizontal = MaterialTheme.spacing.medium
                        ),
                        onMemberClick = onMemberClicked
                    )
                }
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.additionalMovieDetailsInfo.credits?.crew.isNullOrEmpty()
            ) {
                uiState.additionalMovieDetailsInfo.credits?.crew.ifNotNullAndEmpty { members ->
                    MemberSection(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(
                            R.string.movie_details_crew
                        ),
                        members = members,
                        contentPadding = PaddingValues(
                            horizontal = MaterialTheme.spacing.medium
                        ),
                        onMemberClick = onMemberClicked
                    )
                }
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.associatedMovies.collection?.run { parts.isNotEmpty() } == true
            ) {
                val movieCollection = uiState.associatedMovies.collection

                if (movieCollection != null && movieCollection.parts.isNotEmpty()) {
                    PresentableListSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surface
                            )
                            .padding(
                                vertical = MaterialTheme.spacing.small
                            ),
                        title = movieCollection.name,
                        list = movieCollection.parts.sortedBy { part -> part.releaseDate },
                        selectedId = uiState.movieDetails?.id,
                        onPresentableClick = { movieId ->
                            if (movieId != uiState.movieDetails?.id) {
                                onMovieClicked(
                                    movieId
                                )
                            } else {
                                scrollToStart()
                            }
                        }
                    )
                }
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = otherDirectorMoviesState.isNotEmpty()
            ) {
                PresentableSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(
                        id = R.string.movie_details_director_movies,
                        uiState.associatedMovies.directorMovies.directorName
                    ),
                    state = otherDirectorMoviesState,
                    showLoadingAtRefresh = false,
                    showMoreButton = false,
                    onMoreClick = onSimilarMoreClicked,
                    onPresentableClick = { movieId ->
                        if (movieId != uiState.movieDetails?.id) {
                            onMovieClicked(
                                movieId
                            )
                        } else {
                            scrollToStart()
                        }
                    }
                )
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = moviesRecommendationState.isNotEmpty()
            ) {
                PresentableSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(
                        R.string.movie_details_recommendations
                    ),
                    state = moviesRecommendationState,
                    showLoadingAtRefresh = false,
                    onMoreClick = onRecommendationsMoreClicked,
                    onPresentableClick = { movieId ->
                        if (movieId != uiState.movieDetails?.id) {
                            onMovieClicked(
                                movieId
                            )
                        } else {
                            scrollToStart()
                        }
                    }
                )
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = similarMoviesState.isNotEmpty()
            ) {
                val movieCollection = uiState.associatedMovies.collection

                PresentableSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = if (movieCollection != null) {
                        stringResource(
                            R.string.similar_with,
                            movieCollection.name
                        )
                    } else {
                        stringResource(
                            id = R.string.movie_details_similar
                        )
                    },
                    state = similarMoviesState,
                    showLoadingAtRefresh = false,
                    onMoreClick = onSimilarMoreClicked,
                    onPresentableClick = { movieId ->
                        if (movieId != uiState.movieDetails?.id) {
                            onMovieClicked(
                                movieId
                            )
                        } else {
                            scrollToStart()
                        }
                    }
                )
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.associatedContent.videos.isNullOrEmpty()
            ) {
                VideosSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(
                        R.string.season_details_videos_label
                    ),
                    videos = uiState.associatedContent.videos ?: emptyList(),
                    contentPadding = PaddingValues(
                        horizontal = MaterialTheme.spacing.medium
                    ),
                    onVideoClicked = onVideoClicked
                )
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.additionalMovieDetailsInfo.reviewsCount > 0
            ) {
                ReviewSection(
                    modifier = Modifier.fillMaxWidth(),
                    count = uiState.additionalMovieDetailsInfo.reviewsCount,
                    onClick = onReviewsClicked
                )
            }

            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    insets = WindowInsets(
                        bottom = MaterialTheme.spacing.medium
                    )
                )
            )
        }
        DetailsAppBar(
            modifier = Modifier.align(
                Alignment.TopCenter
            ),
            title = null,
            backgroundColor = MaterialTheme.colorScheme.surface,
            scrollState = scrollState,
            transparentScrollValueLimit = topSectionScrollLimitValue,
            action = {
                BackButton(
                    modifier = Modifier,
                    onBackClicked = onBackClicked
                )
            },

            trailing = {
                LikeButton(
                    isFavorite = uiState.additionalMovieDetailsInfo.isFavorite,
                    onClick = {
                        val details = uiState.movieDetails

                        if (details != null) {
                            onFavouriteClicked(
                                details
                            )
                        }
                    }
                )
            }
        )
    }
}