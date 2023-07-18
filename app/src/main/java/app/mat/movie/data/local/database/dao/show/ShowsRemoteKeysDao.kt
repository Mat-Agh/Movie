package app.mat.movie.data.local.database.dao.show

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import app.mat.movie.common.util.ShowTypeEntityConverters
import app.mat.movie.data.local.entity.show.ShowsRemoteKeysEntity
import app.mat.movie.data.local.type.ShowTypeEntity

@TypeConverters(
    ShowTypeEntityConverters::class
)
@Dao
interface ShowsRemoteKeysDao {
    @Query(
        "SELECT * FROM ShowsRemoteKeysEntity WHERE type=:type AND language=:language"
    )
    suspend fun getRemoteKey(
        type: ShowTypeEntity,
        language: String
    ): ShowsRemoteKeysEntity?

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertKey(remoteKey: ShowsRemoteKeysEntity)

    @Query(
        "DELETE FROM ShowsRemoteKeysEntity WHERE type=:type AND language=:language"
    )
    suspend fun deleteRemoteKeysOfType(
        type: ShowTypeEntity,
        language: String
    )
}