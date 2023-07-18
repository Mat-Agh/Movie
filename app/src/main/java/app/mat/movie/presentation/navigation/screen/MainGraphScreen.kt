package app.mat.movie.presentation.navigation.screen

sealed class MainGraphScreen(
    val route: String
) {
    //region Companion Object
    companion object {
        const val MAIN_GRAPH_ROUTE_KEY = "graph_main"
        const val MAIN_SCREEN_ROUTE_KEY = "screen_home"
        const val NAVIGATION_BAR_GRAPH_ROUTE_KEY = "graph_navigation_bar"
    }
    //endregion Companion Object

    //region Screen Objects
    object Main : MainGraphScreen(
        route = MAIN_GRAPH_ROUTE_KEY
    )

    object MainScreen : MainGraphScreen(
        route = MAIN_SCREEN_ROUTE_KEY
    )

    object NavigationBar : MainGraphScreen(
        route = NAVIGATION_BAR_GRAPH_ROUTE_KEY
    )
    //endregion Screen Objects
}