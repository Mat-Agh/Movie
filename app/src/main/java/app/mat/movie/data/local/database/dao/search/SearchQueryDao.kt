package app.mat.movie.data.local.database.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.mat.movie.data.local.entity.search.SearchQueryEntity

@Dao
interface SearchQueryDao {
    @Query(
        "SELECT `query` FROM SearchQueryEntity WHERE `query` LIKE '%' || :query || '%' ORDER BY last_use_date DESC LIMIT 3"
    )
    suspend fun searchQueries(
        query: String
    ): List<String>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun addQuery(
        searchQuery: SearchQueryEntity
    )
}