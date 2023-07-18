package app.mat.movie.data.local.entity.show

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["language"])]
)
data class ShowDetailRemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val language: String,
    val nextPage: Int?,
    val lastUpdated: Long
)
