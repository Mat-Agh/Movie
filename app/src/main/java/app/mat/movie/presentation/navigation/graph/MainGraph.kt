package app.mat.movie.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mat.movie.presentation.navigation.screen.MainGraphScreen
import app.mat.movie.presentation.screen.main.MainScreen
import app.mat.movie.presentation.view.MainViewModel

@Composable
fun MainGraph(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        route = MainGraphScreen.Main.route,
        startDestination = MainGraphScreen.MainScreen.route
    ) {
        composable(
            route = MainGraphScreen.MainScreen.route
        ) {
            MainScreen(
                modifier = modifier,
                mainViewModel = mainViewModel
            )
        }

        composable(
            route = MainGraphScreen.NavigationBar.route
        ) {
            NavigationBarGraph(
                modifier = modifier,
                mainViewModel = mainViewModel,
                navHostController = navHostController
            )
        }
    }
}