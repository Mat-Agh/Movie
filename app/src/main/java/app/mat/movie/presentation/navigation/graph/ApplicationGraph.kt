package app.mat.movie.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import app.mat.movie.common.util.EnterAnimation
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.*
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.BrowseMovieScreenData
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.BrowseShowsScreenData
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.MovieDetailsScreenData
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.PersonDetailsScreenData
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.RelatedMoviesScreenData
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.RelatedShowsScreenData
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.ReviewScreenData
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.SeasonsScreenData
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.ShowDetailsScreenData
import app.mat.movie.presentation.navigation.screen.MainGraphScreen
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.screen.browse.movie.BrowseMoviesScreen
import app.mat.movie.presentation.screen.browse.show.BrowseShowsScreen
import app.mat.movie.presentation.screen.details.movie.MovieDetailsScreen
import app.mat.movie.presentation.screen.details.person.PersonDetailsScreen
import app.mat.movie.presentation.screen.details.show.ShowDetailsScreen
import app.mat.movie.presentation.screen.discover.movie.DiscoverMoviesScreen
import app.mat.movie.presentation.screen.discover.show.DiscoverShowScreen
import app.mat.movie.presentation.screen.related.movies.RelatedMoviesScreen
import app.mat.movie.presentation.screen.related.show.RelatedShowScreen
import app.mat.movie.presentation.screen.reviews.ReviewsScreen
import app.mat.movie.presentation.screen.scanner.ScannerScreen
import app.mat.movie.presentation.screen.search.SearchScreen
import kotlinx.coroutines.FlowPreview

@OptIn(
    FlowPreview::class
)
fun NavGraphBuilder.applicationGraph(
    navHostController: NavHostController
) {
    navigation(
        route = NavigationBarGraphScreen.Application.route,
        startDestination = MainGraphScreen.NavigationBar.route
    ) {
        //region Movie Details Screen
        composable(
            route = MovieDetailsScreen.route,
            arguments = listOf(
                navArgument(
                    name = MovieDetailsScreenData.MOVIE_DETAILS_REQUIRED_ARGUMENT_MOVIE_ID,
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = MovieDetailsScreenData.MOVIE_DETAILS_REQUIRED_ARGUMENT_START_ROUTE,
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterAnimation {
                MovieDetailsScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Movie Details Screen

        //region Show Details Screen
        composable(
            route = ShowDetailsScreen.route,
            arguments = listOf(
                navArgument(
                    name = ShowDetailsScreenData.SHOW_DETAILS_REQUIRED_ARGUMENT_SHOW_ID,
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = ShowDetailsScreenData.SHOW_DETAILS_REQUIRED_ARGUMENT_START_ROUTE,
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterAnimation {
                ShowDetailsScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Show Details Screen

        //region Person Details Screen
        composable(
            route = PersonDetailsScreen.route,
            arguments = listOf(
                navArgument(
                    name = PersonDetailsScreenData.PERSON_DETAILS_REQUIRED_ARGUMENT_PERSON_ID,
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = PersonDetailsScreenData.PERSON_DETAILS_REQUIRED_ARGUMENT_START_ROUTE,
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterAnimation {
                PersonDetailsScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Person Details Screen

        //region Related Movies Screen
        composable(
            route = RelatedMoviesScreen.route,
            arguments = listOf(
                navArgument(
                    name = RelatedMoviesScreenData.RELATED_MOVIES_REQUIRED_ARGUMENT_MOVIE_ID,
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_MEDIA_TYPE,
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_START_ROUTE,
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterAnimation {
                RelatedMoviesScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Related Movies Screen

        //region Related Show Screen
        composable(
            route = RelatedShowsScreen.route,
            arguments = listOf(
                navArgument(
                    name = RelatedShowsScreenData.RELATED_SHOWS_REQUIRED_ARGUMENT_SHOW_ID,
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = RelatedShowsScreenData.RELATED_SHOWS_REQUIRED_ARGUMENT_RELATION_TYPE,
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = RelatedShowsScreenData.RELATED_SHOWS_REQUIRED_ARGUMENT_START_ROUTE,
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterAnimation {
                RelatedShowScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Related Show Screen

        //region Seasons Screen
        composable(
            route = SeasonsScreen.route,
            arguments = listOf(
                navArgument(
                    name = SeasonsScreenData.SEASONS_REQUIRED_ARGUMENT_SHOW_ID,
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = SeasonsScreenData.SEASONS_REQUIRED_ARGUMENT_SEASON_NUMBER,
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = SeasonsScreenData.SEASONS_REQUIRED_ARGUMENT_START_ROUTE,
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterAnimation {
                SearchScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Seasons Screen

        //region Review Screen
        composable(
            route = ReviewScreen.route,
            arguments = listOf(
                navArgument(
                    name = ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_MEDIA_ID,
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_MEDIA_TYPE,
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_MEDIA_TYPE,
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_START_ROUTE,
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterAnimation {
                ReviewsScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Review Screen

        //region Browse Movie Screen
        composable(
            route = BrowseMovieScreen.route,
            arguments = listOf(
                navArgument(
                    name = BrowseMovieScreenData.BROWSE_MOVIE_REQUIRED_ARGUMENT_MOVIE_TYPE
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterAnimation {
                BrowseMoviesScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Browse Movie Screen

        //region Browse Shows Screen
        composable(
            route = BrowseShowsScreen.route,
            arguments = listOf(
                navArgument(
                    name = BrowseShowsScreenData.BROWSE_SHOWS_REQUIRED_ARGUMENT_SHOW_TYPE
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterAnimation {
                BrowseShowsScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Browse Shows Screen

        //region Discover Movie Screen
        composable(
            route = DiscoverMovieScreen.route
        ) {
            EnterAnimation {
                DiscoverMoviesScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Discover Movie Screen

        //region Discover Shows Screen
        composable(
            route = DiscoverShowsScreen.route
        ) {
            EnterAnimation {
                DiscoverShowScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Discover Shows Screen

        //region Scanner Screen
        composable(
            route = ScannerScreen.route
        ) {
            EnterAnimation {
                ScannerScreen(
                    navHostController = navHostController
                )
            }
        }
        //endregion Scanner Screen
    }
}