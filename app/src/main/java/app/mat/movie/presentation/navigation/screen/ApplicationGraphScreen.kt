package app.mat.movie.presentation.navigation.screen

sealed class ApplicationGraphScreen(
    val route: String
) {
    //region Companion Object
    companion object {
        object MovieDetailsScreenData {
            const val MOVIE_DETAILS_SCREEN_ROUTE_KEY = "screen_movie_details"
            const val MOVIE_DETAILS_REQUIRED_ARGUMENT_MOVIE_ID = "movieId"
            const val MOVIE_DETAILS_REQUIRED_ARGUMENT_START_ROUTE = "startRoute"
        }

        object ShowDetailsScreenData {
            const val SHOW_DETAILS_SCREEN_ROUTE_KEY = "screen_show_details"
            const val SHOW_DETAILS_REQUIRED_ARGUMENT_SHOW_ID = "showId"
            const val SHOW_DETAILS_REQUIRED_ARGUMENT_START_ROUTE = "startRoute"
        }

        object PersonDetailsScreenData {
            const val PERSON_DETAILS_SCREEN_ROUTE_KEY = "screen_person_details"
            const val PERSON_DETAILS_REQUIRED_ARGUMENT_PERSON_ID = "personId"
            const val PERSON_DETAILS_REQUIRED_ARGUMENT_START_ROUTE = "startRoute"
        }

        object RelatedMoviesScreenData {
            const val RELATED_MOVIES_SCREEN_ROUTE_KEY = "screen_related_movies"
            const val RELATED_MOVIES_REQUIRED_ARGUMENT_MOVIE_ID = "movieId"
            const val RELATED_MOVIES_REQUIRED_ARGUMENT_RELATION_TYPE = "relationType"
            const val RELATED_MOVIES_REQUIRED_ARGUMENT_START_ROUTE = "startRoute"
        }

        object RelatedShowsScreenData {
            const val RELATED_SHOWS_SCREEN_ROUTE_KEY = "screen_related_shows"
            const val RELATED_SHOWS_REQUIRED_ARGUMENT_SHOW_ID = "showId"
            const val RELATED_SHOWS_REQUIRED_ARGUMENT_RELATION_TYPE = "relationType"
            const val RELATED_SHOWS_REQUIRED_ARGUMENT_START_ROUTE = "startRoute"
        }

        object SeasonsScreenData {
            const val SEASONS_SCREEN_ROUTE_KEY = "screen_seasons"
            const val SEASONS_REQUIRED_ARGUMENT_SHOW_ID = "showId"
            const val SEASONS_REQUIRED_ARGUMENT_SEASON_NUMBER = "seasonNumber"
            const val SEASONS_REQUIRED_ARGUMENT_START_ROUTE = "startRoute"
        }

        object ReviewScreenData {
            const val REVIEW_SCREEN_ROUTE_KEY = "screen_review_details"
            const val REVIEW_REQUIRED_ARGUMENT_MEDIA_ID = "mediaId"
            const val REVIEW_REQUIRED_ARGUMENT_MEDIA_TYPE = "mediaType"
            const val REVIEW_REQUIRED_ARGUMENT_START_ROUTE = "startRoute"
        }

        object BrowseMovieScreenData {
            const val BROWSE_MOVIE_SCREEN_ROUTE_KEY = "screen_browse_movie"
            const val BROWSE_MOVIE_REQUIRED_ARGUMENT_MOVIE_TYPE = "movieType"
        }

        object BrowseShowsScreenData {
            const val BROWSE_SHOWS_SCREEN_ROUTE_KEY = "screen_shows_movie"
            const val BROWSE_SHOWS_REQUIRED_ARGUMENT_SHOW_TYPE = "showType"
        }

        const val DISCOVER_MOVIE_SCREEN_ROUTE_KEY = "screen_discover_movie"
        const val DISCOVER_SHOWS_SCREEN_ROUTE_KEY = "screen_discover_shows"

        const val SCANNER_SCREEN_ROUTE_KEY = "screen_scanner"
    }
    //endregion

    //region Screen Objects
    data object MovieDetailsScreen : ApplicationGraphScreen(
        route = MovieDetailsScreenData.MOVIE_DETAILS_SCREEN_ROUTE_KEY +
                "/{${MovieDetailsScreenData.MOVIE_DETAILS_REQUIRED_ARGUMENT_MOVIE_ID}}" +
                "/{${MovieDetailsScreenData.MOVIE_DETAILS_REQUIRED_ARGUMENT_START_ROUTE}}"
    ) {
        fun passArguments(
            movieId: Int,
            startRoute: String
        ): String = MovieDetailsScreenData.MOVIE_DETAILS_SCREEN_ROUTE_KEY +
                "/$movieId" +
                "/$startRoute"
    }

    data object ShowDetailsScreen : ApplicationGraphScreen(
        route = ShowDetailsScreenData.SHOW_DETAILS_SCREEN_ROUTE_KEY +
                "/{${ShowDetailsScreenData.SHOW_DETAILS_REQUIRED_ARGUMENT_SHOW_ID}}" +
                "/{${ShowDetailsScreenData.SHOW_DETAILS_REQUIRED_ARGUMENT_START_ROUTE}}"
    ) {
        fun passArguments(
            showId: Int,
            startRoute: String
        ): String = ShowDetailsScreenData.SHOW_DETAILS_SCREEN_ROUTE_KEY +
                "/$showId" +
                "/$startRoute"
    }

    data object PersonDetailsScreen : ApplicationGraphScreen(
        route = PersonDetailsScreenData.PERSON_DETAILS_SCREEN_ROUTE_KEY +
                "/{${PersonDetailsScreenData.PERSON_DETAILS_REQUIRED_ARGUMENT_PERSON_ID}}" +
                "/{${PersonDetailsScreenData.PERSON_DETAILS_REQUIRED_ARGUMENT_START_ROUTE}}"
    ) {
        fun passArguments(
            personId: Int,
            startRoute: String
        ): String = PersonDetailsScreenData.PERSON_DETAILS_SCREEN_ROUTE_KEY +
                "/$personId" +
                "/$startRoute"
    }

    data object RelatedMoviesScreen : ApplicationGraphScreen(
        route = RelatedMoviesScreenData.RELATED_MOVIES_SCREEN_ROUTE_KEY +
                "/{${RelatedMoviesScreenData.RELATED_MOVIES_REQUIRED_ARGUMENT_MOVIE_ID}}" +
                "/{${RelatedMoviesScreenData.RELATED_MOVIES_REQUIRED_ARGUMENT_RELATION_TYPE}}" +
                "/{${RelatedMoviesScreenData.RELATED_MOVIES_REQUIRED_ARGUMENT_START_ROUTE}}"
    ) {
        fun passArguments(
            movieId: Int,
            relationType: String,
            startRoute: String
        ): String = RelatedMoviesScreenData.RELATED_MOVIES_SCREEN_ROUTE_KEY +
                "/$movieId" +
                "/$relationType" +
                "/$startRoute"
    }

    data object RelatedShowsScreen : ApplicationGraphScreen(
        route = RelatedShowsScreenData.RELATED_SHOWS_SCREEN_ROUTE_KEY +
                "/{${RelatedShowsScreenData.RELATED_SHOWS_REQUIRED_ARGUMENT_SHOW_ID}}" +
                "/{${RelatedShowsScreenData.RELATED_SHOWS_REQUIRED_ARGUMENT_RELATION_TYPE}}" +
                "/{${RelatedShowsScreenData.RELATED_SHOWS_REQUIRED_ARGUMENT_START_ROUTE}}"
    ) {
        fun passArguments(
            showId: Int,
            relationType: String,
            startRoute: String
        ): String = RelatedShowsScreenData.RELATED_SHOWS_SCREEN_ROUTE_KEY +
                "/$showId" +
                "/$relationType" +
                "/$startRoute"
    }

    data object SeasonsScreen : ApplicationGraphScreen(
        route = SeasonsScreenData.SEASONS_SCREEN_ROUTE_KEY +
                "/{${SeasonsScreenData.SEASONS_REQUIRED_ARGUMENT_SHOW_ID}}" +
                "/{${SeasonsScreenData.SEASONS_REQUIRED_ARGUMENT_SEASON_NUMBER}}" +
                "/{${SeasonsScreenData.SEASONS_REQUIRED_ARGUMENT_START_ROUTE}}"
    ) {
        fun passArguments(
            showId: Int,
            seasonNumber: Int,
            startRoute: String
        ): String = SeasonsScreenData.SEASONS_SCREEN_ROUTE_KEY +
                "/$showId" +
                "/$seasonNumber" +
                "/$startRoute"
    }

    data object ReviewScreen : ApplicationGraphScreen(
        route = ReviewScreenData.REVIEW_SCREEN_ROUTE_KEY +
                "/{${ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_MEDIA_ID}}" +
                "/{${ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_MEDIA_TYPE}}" +
                "/{${ReviewScreenData.REVIEW_REQUIRED_ARGUMENT_START_ROUTE}}"
    ) {
        fun passArguments(
            mediaId: Int,
            mediaType: String,
            startRoute: String
        ): String = ReviewScreenData.REVIEW_SCREEN_ROUTE_KEY +
                "/$mediaId" +
                "/$mediaType" +
                "/$startRoute"
    }

    data object BrowseMovieScreen : ApplicationGraphScreen(
        route = BrowseMovieScreenData.BROWSE_MOVIE_SCREEN_ROUTE_KEY +
                "/{${BrowseMovieScreenData.BROWSE_MOVIE_REQUIRED_ARGUMENT_MOVIE_TYPE}}"
    ) {
        fun passArguments(
            movieType: String
        ): String = BrowseMovieScreenData.BROWSE_MOVIE_SCREEN_ROUTE_KEY + "/$movieType"
    }

    data object BrowseShowsScreen : ApplicationGraphScreen(
        route = BrowseShowsScreenData.BROWSE_SHOWS_SCREEN_ROUTE_KEY +
                "/{${BrowseShowsScreenData.BROWSE_SHOWS_REQUIRED_ARGUMENT_SHOW_TYPE}}"
    ) {
        fun passArguments(
            showType: String
        ): String = BrowseShowsScreenData.BROWSE_SHOWS_SCREEN_ROUTE_KEY + "/$showType"
    }

    data object DiscoverMovieScreen : ApplicationGraphScreen(
        route = DISCOVER_MOVIE_SCREEN_ROUTE_KEY
    )

    data object DiscoverShowsScreen : ApplicationGraphScreen(
        route = DISCOVER_SHOWS_SCREEN_ROUTE_KEY
    )

    data object ScannerScreen : ApplicationGraphScreen(
        route = SCANNER_SCREEN_ROUTE_KEY
    )
    //endregion Screen Objects
}
