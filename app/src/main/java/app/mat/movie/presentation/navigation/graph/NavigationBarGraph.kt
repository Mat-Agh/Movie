package app.mat.movie.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mat.movie.common.util.EnterAnimation
import app.mat.movie.presentation.navigation.screen.MainGraphScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen.MovieScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen.SearchScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen.ShowScreen
import app.mat.movie.presentation.screen.favorite.FavoriteScreen
import app.mat.movie.presentation.screen.movie.MovieScreen
import app.mat.movie.presentation.screen.search.SearchScreen
import app.mat.movie.presentation.screen.show.ShowScreen
import app.mat.movie.presentation.view.MainViewModel

@Composable
fun NavigationBarGraph(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        route = MainGraphScreen.NavigationBar.route,
        startDestination = MovieScreen.route
    ) {
        //Home Screen
        composable(
            route = MovieScreen.route
        ) {
            EnterAnimation {
                MovieScreen(
                    mainViewModel = mainViewModel,
                    navHostController = navHostController
                )
            }
        }

        //Show Screen
        composable(
            route = ShowScreen.route
        ) {
            EnterAnimation {
                ShowScreen(
                    mainViewModel = mainViewModel,
                    navHostController = navHostController
                )
            }
        }

        //Search Screen
        composable(
            route = SearchScreen.route
        ) {
            EnterAnimation {
                SearchScreen(
                    navHostController = navHostController
                )
            }
        }

        //Favorite Screen
        composable(
            route = NavigationBarGraphScreen.FavoriteScreen.route
        ) {
            EnterAnimation {
                FavoriteScreen(
                    navHostController = navHostController
                )
            }
        }

        //Application screens Navigation Graph
        applicationGraph(navHostController = navHostController)
    }
}