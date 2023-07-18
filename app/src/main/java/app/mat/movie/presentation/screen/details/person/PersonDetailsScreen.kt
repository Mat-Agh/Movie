package app.mat.movie.presentation.screen.details.person

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.mat.movie.R
import app.mat.movie.common.util.openExternalId
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.data.remote.type.MediaType
import app.mat.movie.presentation.component.common.AnimatedContentContainer
import app.mat.movie.presentation.component.common.BasicAppBar
import app.mat.movie.presentation.component.dialog.ErrorDialog
import app.mat.movie.presentation.component.section.ExternalIdsSection
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.*
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.screen.details.component.MovplayCreditsList
import app.mat.movie.presentation.screen.details.component.MovplayPersonDetailsTopContent
import app.mat.movie.presentation.screen.details.component.MovplayPersonProfileImage
import app.mat.movie.presentation.screen.details.component.PersonDetailsInfoSection
import app.mat.movie.presentation.theme.spacing
import timber.log.Timber

@Composable
fun AnimatedVisibilityScope.PersonDetailsScreen(
    viewModel: PersonDetailsViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBackClicked: () -> Unit = {
        navHostController.navigateUp()
    }
    val onCloseClicked: () -> Unit = {
        navHostController.popBackStack(
            route = uiState.startRoute ?: NavigationBarGraphScreen.MovieScreen.route,
            inclusive = false
        )
    }
    val onExternalIdClicked = { id: ExternalIdsResource ->
        openExternalId(
            context = context,
            externalId = id
        )
    }
    val onMediaClicked = { mediaType: MediaType, id: Int ->
        val route = when (mediaType) {
            MediaType.Movie -> {
                MovieDetailsScreen.passArguments(
                    movieId = id,
                    startRoute = uiState.startRoute ?: NavigationBarGraphScreen.MovieScreen.route
                )
            }

            MediaType.Tv -> {
                ShowDetailsScreen.passArguments(
                    showId = id,
                    startRoute = uiState.startRoute ?: NavigationBarGraphScreen.MovieScreen.route
                )
            }

            else -> null
        }

        if (route != null) {
            navHostController.navigate(
                route
            )
        }
    }

    PersonDetailsScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onCloseClicked = onCloseClicked,
        onExternalIdClicked = onExternalIdClicked,
        onMediaClicked = onMediaClicked
    )
}

@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun PersonDetailsScreenContent(
    uiState: PersonDetailsScreenUIState,
    onBackClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    onExternalIdClicked: (id: ExternalIdsResource) -> Unit,
    onMediaClicked: (type: MediaType, id: Int) -> Unit
) {
    val personDetailsState by derivedStateOf {
        uiState.details?.let {
            PersonDetailsState.Result(
                it
            )
        } ?: PersonDetailsState.Loading
    }
    var showErrorDialog by remember {
        mutableStateOf(
            false
        )
    }
    LaunchedEffect(
        uiState.error
    ) {
        showErrorDialog = uiState.error != null
        Timber.tag("TAG").e("PersonDetailsScreenContent: ")
    }
    BackHandler(showErrorDialog) {
        showErrorDialog = false
    }

    if (showErrorDialog) {
        ErrorDialog(onDismissRequest = {
            showErrorDialog = false
        }, onConfirmClick = {
            showErrorDialog = false
        })
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(
                    top = 56.dp
                )
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium
            )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        MaterialTheme.spacing.medium
                    )
            ) {
                val (profileImageRef, contentRef) = createRefs()
                MovplayPersonProfileImage(
                    modifier = Modifier.constrainAs(
                        profileImageRef
                    ) {
                        top.linkTo(
                            parent.top
                        )
                        start.linkTo(
                            parent.start
                        )
                    },
                    personDetailsState = personDetailsState
                )
                Column(
                    modifier = Modifier
                        .constrainAs(
                            contentRef
                        ) {
                            start.linkTo(
                                profileImageRef.end
                            )
                            end.linkTo(
                                parent.end
                            )
                            top.linkTo(
                                profileImageRef.top
                            )
                            bottom.linkTo(
                                profileImageRef.bottom
                            )

                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }
                        .padding(
                            start = MaterialTheme.spacing.medium
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(
                                MaterialTheme.spacing.small
                            )
                    ) {
                        MovplayPersonDetailsTopContent(
                            modifier = Modifier.fillMaxWidth(),
                            personDetails = uiState.details
                        )
                    }
                    Spacer(
                        modifier = Modifier.weight(
                            1f
                        )
                    )

                    uiState.externalIds?.let { ids ->
                        ExternalIdsSection(
                            modifier = Modifier.fillMaxWidth(),
                            externalIds = ids,
                            onExternalIdClick = onExternalIdClicked
                        )
                    }
                }
            }

            PersonDetailsInfoSection(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(),
                personDetails = uiState.details
            )

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.credits?.cast.isNullOrEmpty()
            ) {
                MovplayCreditsList(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(
                        R.string.person_details_screen_cast
                    ),
                    credits = uiState.credits?.cast ?: emptyList(),
                    onCreditsClick = onMediaClicked
                )
            }

            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.credits?.crew.isNullOrEmpty()
            ) {
                MovplayCreditsList(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(
                        R.string.person_details_screen_crew
                    ),
                    credits = uiState.credits?.crew ?: emptyList(),
                    onCreditsClick = onMediaClicked
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

        BasicAppBar(
            modifier = Modifier.align(
                Alignment.TopCenter
            ),
            title = stringResource(
                R.string.person_details_screen_appbar_label
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
        )
    }
}