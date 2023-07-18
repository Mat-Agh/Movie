package app.mat.movie.data.local.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import app.mat.movie.common.util.MovieTypeEntityConverters
import app.mat.movie.data.local.entity.movie.MovieDetailsRemoteKeyEntity

@TypeConverters(
    MovieTypeEntityConverters::class
)
@Dao
interface MovieDetailsRemoteKeysDao {
    @Query(
        "SELECT * FROM MovieDetailsRemoteKeyEntity WHERE language=:language LIMIT 1"
    )
    suspend fun getRemoteKey(
        language: String
    ): MovieDetailsRemoteKeyEntity?

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertKey(
        remoteKey: MovieDetailsRemoteKeyEntity
    )

    @Query(
        "DELETE FROM MovieDetailsRemoteKeyEntity WHERE language=:language"
    )
    suspend fun deleteKeys(
        language: String
    )
}