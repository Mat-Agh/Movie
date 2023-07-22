package app.mat.movie.presentation.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import app.mat.movie.R

sealed class NavigationBarGraphScreen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    //region Companion
    companion object {
        const val MOVIE_SCREEN_ROUTE_KEY = "screen_movie"
        val MOVIE_SCREEN_TITLE = R.string.movies_label

        const val SHOW_SCREEN_ROUTE_KEY = "screen_show"
        val SHOW_SCREEN_TITLE = R.string.shows_label

        const val FAVORITE_SCREEN_ROUTE_KEY = "screen_favorite"
        val FAVORITE_SCREEN_TITLE = R.string.favourites_label

        const val SEARCH_SCREEN_ROUTE_KEY = "screen_search"
        val SEARCH_SCREEN_TITLE = R.string.search_label

        const val APPLICATION_GRAPH_ROUTE_KEY = "graph_application"
    }
    //endregion Companion

    //region Screen Objects
    data object MovieScreen : NavigationBarGraphScreen(
        route = MOVIE_SCREEN_ROUTE_KEY,
        title = MOVIE_SCREEN_TITLE,
        icon = Icons.Filled.Movie
    )

    data object ShowScreen : NavigationBarGraphScreen(
        route = SHOW_SCREEN_ROUTE_KEY,
        title = SHOW_SCREEN_TITLE,
        icon = Icons.Filled.LiveTv
    )

    data object FavoriteScreen : NavigationBarGraphScreen(
        route = FAVORITE_SCREEN_ROUTE_KEY,
        title = FAVORITE_SCREEN_TITLE,
        icon = Icons.Filled.Favorite
    )

    data object SearchScreen : NavigationBarGraphScreen(
        route = SEARCH_SCREEN_ROUTE_KEY,
        title = SEARCH_SCREEN_TITLE,
        icon = Icons.Filled.Search
    )

    data object Application : MainGraphScreen(
        route = APPLICATION_GRAPH_ROUTE_KEY
    )
    //endregion Screen Objects
}