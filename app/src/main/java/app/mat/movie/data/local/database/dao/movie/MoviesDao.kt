package app.mat.movie.data.local.database.dao.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import app.mat.movie.common.util.MovieTypeEntityConverters
import app.mat.movie.data.local.entity.movie.MovieEntity
import app.mat.movie.data.local.type.MovieTypeEntity

@TypeConverters(
    MovieTypeEntityConverters::class
)
@Dao
interface MoviesDao {
    @Query(
        "SELECT * FROM MovieEntity WHERE type=:type And language=:language"
    )
    fun getAllMovies(
        type: MovieTypeEntity,
        language: String
    ): PagingSource<Int, MovieEntity>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun addMovies(
        movies: List<MovieEntity>
    )

    @Query(
        "DELETE FROM MovieEntity WHERE type=:type AND language=:language"
    )
    suspend fun deleteMoviesOfType(
        type: MovieTypeEntity,
        language: String
    )
}