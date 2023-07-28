package app.mat.movie.presentation.screen.details.show

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import app.mat.movie.common.type.RelationType
import app.mat.movie.common.util.isNotEmpty
import app.mat.movie.common.util.openExternalId
import app.mat.movie.common.util.openVideo
import app.mat.movie.common.util.shareImdb
import app.mat.movie.common.util.toJsonString
import app.mat.movie.data.remote.dto.common.ShareDetailsModel
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
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
import app.mat.movie.presentation.component.section.PresentableSection
import app.mat.movie.presentation.component.section.ReviewSection
import app.mat.movie.presentation.component.section.SeasonSection
import app.mat.movie.presentation.component.section.VideosSection
import app.mat.movie.presentation.component.section.WatchProvidersSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.*
import app.mat.movie.presentation.screen.details.component.ShowDetailsInfoSection
import app.mat.movie.presentation.screen.details.component.ShowDetailsTopContent
import app.mat.movie.presentation.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun AnimatedVisibilityScope.ShowDetailsScreen(
    viewModel: ShowDetailsScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val onBackClicked: () -> Unit = {
        navHostController.navigateUp()
    }

    val onFavoriteClicked: (details: ShowDetailsDto) -> Unit = { details ->
        if (uiState.additionalTvShowDetailsInfo.isFavorite) {
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
            uiState.startRoute,
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

    val onCreatorClicked: (personId: Int) -> Unit = { personId: Int ->
        navHostController.navigate(
            PersonDetailsScreen.passArguments(
                personId = personId,
                startRoute = uiState.startRoute
            )
        )
    }

    val onTvShowClicked: (showId: Int) -> Unit = { showId: Int ->
        navHostController.navigate(
            ShowDetailsScreen.passArguments(
                showId = showId,
                startRoute = uiState.startRoute
            )
        )
    }

    val onReviewsClicked: () -> Unit = {
        val showId = uiState.showDetails?.id

        if (showId != null) {
            navHostController.navigate(
                ReviewScreen.passArguments(
                    mediaId = showId,
                    mediaType = MediaType.Tv.toJsonString(),
                    startRoute = uiState.startRoute
                )
            )
        }
    }

    val onSeasonClicked = { seasonNumber: Int ->
        val showId = uiState.showDetails?.id

        if (showId != null) {
            navHostController.navigate(
                SeasonsScreen.passArguments(
                    showId = showId,
                    seasonNumber = seasonNumber,
                    startRoute = uiState.startRoute
                )
            )
        }
    }

    val onSimilarMoreClicked = {
        val showId = uiState.showDetails?.id

        if (showId != null) {
            navHostController.navigate(
                RelatedShowsScreen.passArguments(
                    showId = showId,
                    relationType = RelationType.Similar.toJsonString(),
                    startRoute = uiState.startRoute
                )
            )
        }
    }

    val onRecommendationsMoreClicked = {
        val showId = uiState.showDetails?.id

        if (showId != null) {
            navHostController.navigate(
                RelatedShowsScreen.passArguments(
                    showId = showId,
                    relationType = RelationType.Recommended.toJsonString(),
                    startRoute = uiState.startRoute
                )
            )
        }
    }

    TvShowDetailsScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onExternalIdClicked = onExternalIdClicked,
        onShareClicked = onShareClicked,
        onVideoClicked = onVideoClicked,
        onFavoriteClicked = onFavoriteClicked,
        onCloseClicked = onCloseClicked,
        onCreatorClicked = onCreatorClicked,
        onTvShowClicked = onTvShowClicked,
        onSeasonClicked = onSeasonClicked,
        onSimilarMoreClicked = onSimilarMoreClicked,
        onRecommendationsMoreClicked = onRecommendationsMoreClicked,
        onReviewsClicked = onReviewsClicked
    )
}

@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun TvShowDetailsScreenContent(
    uiState: ShowDetailsScreenUIState,
    onBackClicked: () -> Unit,
    onExternalIdClicked: (id: ExternalIdsResource) -> Unit,
    onShareClicked: (details: ShareDetailsModel) -> Unit,
    onVideoClicked: (video: VideoDto) -> Unit,
    onFavoriteClicked: (details: ShowDetailsDto) -> Unit,
    onCloseClicked: () -> Unit,
    onCreatorClicked: (personId: Int) -> Unit,
    onTvShowClicked: (tvShowId: Int) -> Unit,
    onSeasonClicked: (seasonNumber: Int) -> Unit,
    onSimilarMoreClicked: () -> Unit,
    onRecommendationsMoreClicked: () -> Unit,
    onReviewsClicked: () -> Unit
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val similar = uiState.associatedTvShow.similar.collectAsLazyPagingItems()
    val recommendations = uiState.associatedTvShow.recommendations.collectAsLazyPagingItems()

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
    val appbarHeight = density.run {
        56.dp.toPx()
    }
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
                presentable = uiState.showDetails,
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
                    ShowDetailsTopContent(
                        modifier = Modifier.fillMaxWidth(),
                        showDetails = uiState.showDetails
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

            ShowDetailsInfoSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium
                    )
                    .animateContentSize(),
                showDetails = uiState.showDetails,
                nextEpisodeDaysRemaining = uiState.additionalTvShowDetailsInfo.nextEpisodeRemainingDays,
                imdbExternalId = imdbExternalId,
                onShareClicked = onShareClicked
            )

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.additionalTvShowDetailsInfo.watchProviders != null
            ) {
                if (uiState.additionalTvShowDetailsInfo.watchProviders != null) {
                    WatchProvidersSection(
                        modifier = Modifier.fillMaxWidth(),
                        watchProviders = uiState.additionalTvShowDetailsInfo.watchProviders,
                        title = stringResource(
                            R.string.available_at
                        )
                    )
                }
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.showDetails?.creators.isNullOrEmpty()
            ) {
                MemberSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(
                        R.string.tv_series_details_creators
                    ),
                    members = uiState.showDetails?.creators ?: emptyList(),
                    contentPadding = PaddingValues(
                        horizontal = MaterialTheme.spacing.medium
                    ),
                    onMemberClick = onCreatorClicked
                )
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.showDetails?.seasons.isNullOrEmpty()
            ) {
                SeasonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface
                        )
                        .padding(
                            vertical = MaterialTheme.spacing.small
                        ),
                    title = stringResource(
                        R.string.tv_series_details_seasons
                    ),
                    seasons = uiState.showDetails?.seasons ?: emptyList(),
                    onSeasonClick = onSeasonClicked
                )
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = recommendations.isNotEmpty()
            ) {
                PresentableSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(
                        R.string.tv_series_details_recommendations
                    ),
                    state = recommendations,
                    showLoadingAtRefresh = false,
                    onMoreClick = onRecommendationsMoreClicked,
                    onPresentableClick = { tvShowId ->
                        if (tvShowId != uiState.showDetails?.id) {
                            onTvShowClicked(
                                tvShowId
                            )
                        } else {
                            scrollToStart()
                        }
                    }
                )
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = similar.isNotEmpty()
            ) {
                PresentableSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(
                        R.string.tv_series_details_similar
                    ),
                    state = similar,
                    showLoadingAtRefresh = false,
                    onMoreClick = onSimilarMoreClicked,
                    onPresentableClick = { tvShowId ->
                        if (tvShowId != uiState.showDetails?.id) {
                            onTvShowClicked(
                                tvShowId
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
                        R.string.tv_series_details_videos
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
                visible = uiState.additionalTvShowDetailsInfo.reviewsCount > 0
            ) {
                ReviewSection(
                    modifier = Modifier.fillMaxWidth(),
                    count = uiState.additionalTvShowDetailsInfo.reviewsCount,
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

                    isFavorite = uiState.additionalTvShowDetailsInfo.isFavorite,
                    onClick = {
                        val details = uiState.showDetails

                        if (details != null) {
                            onFavoriteClicked(
                                details
                            )
                        }
                    }
                )
            }
        )
    }
}