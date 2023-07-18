package app.mat.movie.data.local.database.dao.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import app.mat.movie.common.util.MovieTypeEntityConverters
import app.mat.movie.data.local.entity.movie.MovieDetailEntity

@TypeConverters(
    MovieTypeEntityConverters::class
)
@Dao
interface MovieDetailsDao {
    @Query(
        "SELECT * FROM MovieDetailEntity WHERE language=:language"
    )
    fun getALlMovies(
        language: String
    ): PagingSource<Int, MovieDetailEntity>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun addMovies(
        movies: List<MovieDetailEntity>
    )

    @Query(
        "DELETE FROM MovieDetailEntity WHERE language=:language"
    )
    suspend fun deleteMovieDetails(
        language: String
    )
}