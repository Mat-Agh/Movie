package app.mat.movie.data.local.database.dao.show

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.mat.movie.data.local.entity.show.ShowFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteShowsDao {
    @Query(
        "SELECT * FROM ShowFavoriteEntity ORDER BY added_date DESC"
    )
    fun getAllFavoriteTvShows(): DataSource.Factory<Int, ShowFavoriteEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun likeTvShow(
        vararg tvShowFavorite: ShowFavoriteEntity
    )

    @Query(
        "DELETE FROM ShowFavoriteEntity WHERE id=:tvShowId"
    )
    suspend fun unlikeTvShow(
        tvShowId: Int
    )

    @Query(
        "SELECT id FROM ShowFavoriteEntity"
    )
    fun favoriteTvShowIds(): Flow<List<Int>>

    @Query(
        "SELECT COUNT(id) FROM ShowFavoriteEntity"
    )
    fun favoriteTvShowCount(): Flow<Int>
}