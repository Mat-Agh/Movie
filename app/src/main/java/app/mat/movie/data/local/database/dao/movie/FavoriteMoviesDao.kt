package app.mat.movie.data.local.database.dao.movie

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.mat.movie.data.local.entity.movie.MovieFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMoviesDao {
    @Query(
        "Select * From MovieFavoriteEntity ORDER BY added_date DESC"
    )
    fun getAllFavoriteMovies(): DataSource.Factory<Int, MovieFavoriteEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun likeMovie(
        vararg movieDetails: MovieFavoriteEntity
    )

    @Query(
        "DELETE FROM MovieFavoriteEntity WHERE id = :movieId"
    )
    suspend fun unlikeMovie(
        movieId: Int
    )

    @Query(
        "SELECT id FROM MovieFavoriteEntity"
    )
    fun favoriteMoviesIds(): Flow<List<Int>>

    @Query(
        "SELECT COUNT(id) FROM MovieFavoriteEntity"
    )
    fun favoriteMoviesCount(): Flow<Int>
}