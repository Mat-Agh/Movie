package app.mat.movie.presentation.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.mat.movie.common.type.SnackBarEvent
import app.mat.movie.presentation.component.common.NavigationBar
import app.mat.movie.presentation.navigation.graph.NavigationBarGraph
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.theme.MovieAppTheme
import app.mat.movie.presentation.theme.spacing
import app.mat.movie.presentation.view.LocalImageUrlParser
import app.mat.movie.presentation.view.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import timber.log.Timber

@SuppressLint(
    "UnrememberedMutableState",
    "RestrictedApi"
)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val imageUrlParser by mainViewModel.imageUrlParser.collectAsStateWithLifecycle()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val snackBarEvent: SnackBarEvent? by mainViewModel.networkSnackBarEvent.collectAsStateWithLifecycle()
    val navHostController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    var currentRoute: String? by rememberSaveable {
        mutableStateOf(
            null,
        )
    }

    var backQueueRoutes: List<String?> by rememberSaveable {
        mutableStateOf(
            emptyList()
        )
    }

    navHostController.apply {
        addOnDestinationChangedListener { controller, _, _ ->
            currentRoute = controller.currentBackStackEntry?.destination?.route
            backQueueRoutes = controller.currentBackStack.value.map { entry -> entry.destination.route }
        }
        addOnDestinationChangedListener { _, _, _ ->
            keyboardController?.hide()
        }
    }

    val showBottomBar by derivedStateOf {
        currentRoute in setOf(
            null,
            NavigationBarGraphScreen.MovieScreen.route,
            NavigationBarGraphScreen.ShowScreen.route,
            NavigationBarGraphScreen.FavoriteScreen.route,
            NavigationBarGraphScreen.SearchScreen.route
        )
    }

    val context = LocalContext.current

    LaunchedEffect(
        snackBarEvent,
        context
    ) {
        snackBarEvent?.let { event ->
            snackBarHostState.showSnackbar(
                message = getString(
                    context,
                    event.messageStringRes,
                )
            )
        }
    }

    LaunchedEffect(
        lifeCycleOwner
    ) {
        lifeCycleOwner.repeatOnLifecycle(
            Lifecycle.State.STARTED
        ) {
            Timber.d(
                "Update locale"
            )

            mainViewModel.updateLocale()
        }
    }

    CompositionLocalProvider(
        LocalImageUrlParser provides imageUrlParser
    ) {
        MovieAppTheme(
            darkTheme = isSystemInDarkTheme(),
            dynamicColor = false
        ) {
            val navigationBarColor = MaterialTheme.colorScheme.surface
            val experiment = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            val checkTheme = isSystemInDarkTheme()

            SideEffect {
                if (checkTheme) {
                    systemUiController.setStatusBarColor(
                        color = experiment,
                        darkIcons = false
                    )
                } else {
                    systemUiController.setStatusBarColor(
                        color = experiment,
                        darkIcons = true
                    )
                }
                systemUiController.setNavigationBarColor(
                    color = navigationBarColor,
                    darkIcons = true
                )
            }

            HomeScreenContent(
                modifier = modifier,
                mainViewModel = mainViewModel,
                navHostController = navHostController,
                showBottomBar = showBottomBar
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    showBottomBar: Boolean
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                snackBarHostState
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = modifier.navigationBarsPadding(),
                mainViewModel = mainViewModel,
                navHostController = navHostController,
                isVisible = showBottomBar
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = if (showBottomBar) {
                        innerPadding.calculateBottomPadding()
                    } else {
                        MaterialTheme.spacing.small
                    }
                ),
            color = MaterialTheme.colorScheme.background
        ) {
            NavigationBarGraph(
                mainViewModel = mainViewModel,
                navHostController = navHostController
            )
        }
    }
}