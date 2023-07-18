package app.mat.movie.data.local.database.dao.show

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import app.mat.movie.common.util.ShowTypeEntityConverters
import app.mat.movie.data.local.entity.show.ShowDetailRemoteKeysEntity

@TypeConverters(
    ShowTypeEntityConverters::class
)
@Dao
interface ShowsDetailsRemoteKeysDao {
    @Query(
        "SELECT * FROM ShowDetailRemoteKeysEntity WHERE language=:language"
    )
    suspend fun getRemoteKey(
        language: String
    ): ShowDetailRemoteKeysEntity?

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertKey(
        remoteKey: ShowDetailRemoteKeysEntity
    )

    @Query(
        "DELETE FROM ShowDetailRemoteKeysEntity WHERE language=:language"
    )
    suspend fun deleteKeys(
        language: String
    )
}