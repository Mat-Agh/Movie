package app.mat.movie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.mat.movie.common.util.DateConverters
import app.mat.movie.data.local.database.dao.movie.FavoriteMoviesDao
import app.mat.movie.data.local.database.dao.movie.MovieDetailsDao
import app.mat.movie.data.local.database.dao.movie.MovieDetailsRemoteKeysDao
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
import app.mat.movie.data.local.entity.movie.MovieDetailEntity
import app.mat.movie.data.local.entity.movie.MovieDetailsRemoteKeyEntity
import app.mat.movie.data.local.entity.movie.MovieEntity
import app.mat.movie.data.local.entity.movie.MovieFavoriteEntity
import app.mat.movie.data.local.entity.movie.MoviesRemoteKeysEntity
import app.mat.movie.data.local.entity.movie.RecentlyBrowsedMovieEntity
import app.mat.movie.data.local.entity.search.SearchQueryEntity
import app.mat.movie.data.local.entity.show.RecentlyBrowsedShowEntity
import app.mat.movie.data.local.entity.show.ShowDetailRemoteKeysEntity
import app.mat.movie.data.local.entity.show.ShowDetailsEntity
import app.mat.movie.data.local.entity.show.ShowEntity
import app.mat.movie.data.local.entity.show.ShowFavoriteEntity
import app.mat.movie.data.local.entity.show.ShowsRemoteKeysEntity

@Database(
    entities = [
        MovieFavoriteEntity::class,
        ShowFavoriteEntity::class,
        RecentlyBrowsedMovieEntity::class,
        RecentlyBrowsedShowEntity::class,
        SearchQueryEntity::class,
        MovieEntity::class,
        ShowEntity::class,
        MoviesRemoteKeysEntity::class,
        ShowsRemoteKeysEntity::class,
        MovieDetailEntity::class,
        ShowDetailsEntity::class,
        MovieDetailsRemoteKeyEntity::class,
        ShowDetailRemoteKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverters::class
)
abstract class Database : RoomDatabase() {
    //region Movie Dao
    abstract fun moviesDao(): MoviesDao

    abstract fun favoriteMoviesDao(): FavoriteMoviesDao

    abstract fun recentlyBrowsedMovies(): RecentlyBrowsedMoviesDao

    abstract fun moviesRemoteKeysDao(): MoviesRemoteKeysDao

    abstract fun moviesDetailsDao(): MovieDetailsDao

    abstract fun moviesDetailsRemoteKeysDao(): MovieDetailsRemoteKeysDao
    //endregion Movie Dao

    //region Tv Dao
    abstract fun showsDao(): ShowsDao

    abstract fun favoritesShowsDao(): FavoriteShowsDao

    abstract fun recentlyBrowsedTvShows(): RecentlyBrowsedTvShowsDao

    abstract fun showsRemoteKeysDao(): ShowsRemoteKeysDao

    abstract fun showsDetailsDao(): ShowsDetailsDao

    abstract fun showsDetailsRemoteKeysDao(): ShowsDetailsRemoteKeysDao
    //endregion Tv Dao

    //region Search Dao
    abstract fun searchQueryDao(): SearchQueryDao
    //endregion Dao
}