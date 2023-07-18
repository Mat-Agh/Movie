package app.mat.movie.data.local.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import app.mat.movie.common.util.MovieTypeEntityConverters
import app.mat.movie.data.local.entity.movie.MoviesRemoteKeysEntity
import app.mat.movie.data.local.type.MovieTypeEntity

@TypeConverters(
    MovieTypeEntityConverters::class
)
@Dao
interface MoviesRemoteKeysDao {
    @Query(
        "SELECT * FROM MoviesRemoteKeysEntity WHERE type=:type AND language=:language"
    )
    suspend fun getRemoteKey(
        type: MovieTypeEntity,
        language: String
    ): MoviesRemoteKeysEntity?

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertKey(
        remoteKey: MoviesRemoteKeysEntity
    )

    @Query(
        "DELETE FROM MoviesRemoteKeysEntity WHERE type=:type AND language=:language"
    )
    suspend fun deleteRemoteKeysOfType(
        type: MovieTypeEntity,
        language: String
    )
}