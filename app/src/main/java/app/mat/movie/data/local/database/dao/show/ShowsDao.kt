package app.mat.movie.data.local.database.dao.show

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import app.mat.movie.common.util.ShowTypeEntityConverters
import app.mat.movie.data.local.entity.show.ShowEntity
import app.mat.movie.data.local.type.ShowTypeEntity

@TypeConverters(
    ShowTypeEntityConverters::class
)
@Dao
interface ShowsDao {
    @Query(
        "SELECT * FROM ShowEntity WHERE type=:type AND language=:language"
    )
    fun getAllTvShows(
        type: ShowTypeEntity,
        language: String
    ): PagingSource<Int, ShowEntity>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun addTvShow(
        shows: List<ShowEntity>
    )

    @Query(
        "DELETE FROM ShowEntity WHERE type=:type AND language=:language"
    )
    suspend fun deleteTvShowsOfType(
        type: ShowTypeEntity,
        language: String
    )
}