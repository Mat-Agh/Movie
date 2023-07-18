package app.mat.movie.data.local.database.dao.show

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.mat.movie.data.local.entity.show.RecentlyBrowsedShowEntity

@Dao
interface RecentlyBrowsedTvShowsDao {
    @Query(
        "SELECT * FROM RecentlyBrowsedShowEntity ORDER BY added_date DESC"
    )
    fun recentBrowsedTvShow(): DataSource.Factory<Int, RecentlyBrowsedShowEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun addRecentlyBrowsedTvShow(
        vararg recentlyBrowsedMovie: RecentlyBrowsedShowEntity
    )

    @Query("SELECT COUNT(id) FROM RecentlyBrowsedShowEntity")
    suspend fun recentlyBrowsedTvShowCount(): Int

    @Query(
        "DELETE FROM RecentlyBrowsedShowEntity WHERE id IN (SELECT id FROM RecentlyBrowsedShowEntity ORDER BY added_date ASC LIMIT :limit)"
    )
    suspend fun deleteLast(
        limit: Int = 1
    )

    suspend fun deleteAndAdd(
        vararg recentlyBrowsedTvSeries: RecentlyBrowsedShowEntity,
        maxItems: Int = 100
    ) {
        val addCount = recentlyBrowsedTvSeries.count()
        val currentCount = recentlyBrowsedTvShowCount()
        val removeCount = (currentCount + addCount - maxItems).coerceAtLeast(0)

        deleteLast(
            removeCount
        )

        addRecentlyBrowsedTvShow(*recentlyBrowsedTvSeries)
    }

    @Query(
        "DELETE FROM RecentlyBrowsedShowEntity"
    )
    suspend fun clear()
}