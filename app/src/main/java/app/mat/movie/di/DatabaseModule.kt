package app.mat.movie.di

import android.content.Context
import androidx.room.Room
import app.mat.movie.data.local.database.Database
import app.mat.movie.data.local.database.dao.movie.FavoriteMoviesDao
import app.mat.movie.data.local.database.dao.movie.MovieDetailsDao
import app.mat.movie.data.local.database.dao.movie.MoviesDao
import app.mat.movie.data.local.database.dao.movie.MoviesRemoteKeysDao
import app.mat.movie.data.local.database.dao.movie.RecentlyBrowsedMoviesDao
import app.mat.movie.data.local.database.dao.search.SearchQueryDao
import app.mat.movie.data.local.database.dao.show.FavoriteShowsDao
import app.mat.movie.data.local.database.dao.show.RecentlyBrowsedTvShowsDao
import app.mat.movie.data.local.database.dao.show.ShowsDao
import app.mat.movie.data.local.database.dao.show.ShowsDetailsDao
import app.mat.movie.data.local.database.dao.show.ShowsDetailsRemoteKeysDao
import app.mat.movie.data.local.database.dao.show.ShowsRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(
    SingletonComponent::class
)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        Database::class.java,
        "app_database"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideFavoritesMoviesDao(
        database: Database
    ): FavoriteMoviesDao = database.favoriteMoviesDao()

    @Provides
    fun provideFavoriteTvSeriesDao(
        database: Database
    ): FavoriteShowsDao = database.favoritesShowsDao()

    @Provides
    fun provideRecentlyBrowsedMoviesDao(
        database: Database
    ): RecentlyBrowsedMoviesDao = database.recentlyBrowsedMovies()

    @Provides
    fun provideRecentlyBrowsedTvShowsDao(
        database: Database
    ): RecentlyBrowsedTvShowsDao = database.recentlyBrowsedTvShows()

    @Provides
    fun provideSearchQueryDao(
        database: Database
    ): SearchQueryDao = database.searchQueryDao()

    @Provides
    fun provideMoviesDao(
        database: Database
    ): MoviesDao = database.moviesDao()

    @Provides
    fun provideMovieRemoteKeysDao(
        database: Database
    ): MoviesRemoteKeysDao = database.moviesRemoteKeysDao()

    @Provides
    fun provideTvShowsDao(
        database: Database
    ): ShowsDao = database.showsDao()

    @Provides
    fun provideTvShowsRemoteKeysDao(
        database: Database
    ): ShowsRemoteKeysDao = database.showsRemoteKeysDao()

    @Provides
    fun provideMovieDetailsDao(
        database: Database
    ): MovieDetailsDao = database.moviesDetailsDao()

    @Provides
    fun provideMovieDetailsRemoteKeysDao(
        database: Database
    ): MoviesRemoteKeysDao = database.moviesRemoteKeysDao()

    @Provides
    fun provideTvShowDetailsDao(
        database: Database
    ): ShowsDetailsDao = database.showsDetailsDao()

    @Provides
    fun provideTbShowDetailsRemoteKeysDao(
        database: Database
    ): ShowsDetailsRemoteKeysDao = database.showsDetailsRemoteKeysDao()
}