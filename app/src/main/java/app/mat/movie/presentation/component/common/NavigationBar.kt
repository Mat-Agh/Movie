package app.mat.movie.presentation.component.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.mat.movie.presentation.component.item.AddItem
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.view.MainViewModel

@Composable
fun NavigationBar(
    modifier: Modifier,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    isVisible: Boolean = true
) {
    val screens = remember {
        mutableSetOf(
            NavigationBarGraphScreen.MovieScreen,
            NavigationBarGraphScreen.ShowScreen,
            NavigationBarGraphScreen.SearchScreen,
            NavigationBarGraphScreen.FavoriteScreen
        )
    }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigationBarDestination = screens.any {
        it.route == currentDestination?.route
    }

    if (navigationBarDestination) {
        AnimatedVisibility(
            visibleState = MutableTransitionState(
                isVisible
            ),
            enter = slideInVertically {
                it
            },
            exit = slideOutVertically {
                it
            }
        ) {
            NavigationBar {
                screens.forEach { screens ->
                    AddItem(
                        modifier = modifier,
                        mainViewModel = mainViewModel,
                        screen = screens,
                        currentDestination = currentDestination,
                        navController = navHostController
                    )
                }
            }
        }
    }
}