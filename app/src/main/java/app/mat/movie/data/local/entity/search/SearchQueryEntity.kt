package app.mat.movie.data.local.entity.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class SearchQueryEntity(
    @PrimaryKey
    @ColumnInfo(index = true)
    val query: String,
    @ColumnInfo(name = "last_use_date")
    val lastUseDate: Date
)
