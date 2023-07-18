package app.mat.movie.data.local.database.dao.movie

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.mat.movie.data.local.entity.movie.RecentlyBrowsedMovieEntity

@Dao
interface RecentlyBrowsedMoviesDao {
    @Query(
        "SELECT * FROM RecentlyBrowsedMovieEntity ORDER BY added_date DESC"
    )
    fun recentBrowsedMovie(): DataSource.Factory<Int, RecentlyBrowsedMovieEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun addRecentlyBrowsedMovie(
        vararg recentlyBrowsedMovie: RecentlyBrowsedMovieEntity
    )

    @Query(
        "SELECT COUNT(id) FROM RecentlyBrowsedMovieEntity"
    )
    suspend fun recentlyBrowsedMovieCount(): Int

    @Query(
        "DELETE FROM RecentlyBrowsedMovieEntity WHERE id IN (SELECT id FROM RecentlyBrowsedMovieEntity ORDER BY added_date ASC LIMIT :limit)"
    )
    suspend fun deleteLast(
        limit: Int = 1
    )

    suspend fun deleteAndAdd(
        vararg recentlyBrowsedMovieEntity: RecentlyBrowsedMovieEntity,
        maxItems: Int = 100
    ) {
        val addCount = recentlyBrowsedMovieEntity.count()
        val currentCount = recentlyBrowsedMovieCount()
        val removeCount = (currentCount + addCount - maxItems)
            .coerceAtLeast(
                0
            )

        deleteLast(
            removeCount
        )
        addRecentlyBrowsedMovie(
            *recentlyBrowsedMovieEntity
        )
    }

    @Query(
        "DELETE FROM RecentlyBrowsedMovieEntity"
    )
    suspend fun clear()
}