package app.mat.movie.data.local.database.dao.show

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import app.mat.movie.common.util.ShowTypeEntityConverters
import app.mat.movie.data.local.entity.show.ShowDetailsEntity

@TypeConverters(
    ShowTypeEntityConverters::class
)
@Dao
interface ShowsDetailsDao {
    @Query(
        "SELECT * FROM ShowDetailsEntity WHERE language=:language"
    )
    fun getAllTvShow(
        language: String
    ): PagingSource<Int, ShowDetailsEntity>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun addTvShow(
        movies: List<ShowDetailsEntity>
    )

    @Query(
        "DELETE FROM ShowDetailsEntity WHERE language=:language"
    )
    suspend fun deleteAllTvShows(
        language: String
    )
}