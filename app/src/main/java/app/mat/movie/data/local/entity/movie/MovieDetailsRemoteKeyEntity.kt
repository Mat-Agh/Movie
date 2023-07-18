package app.mat.movie.data.local.entity.movie

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["language"])]
)
data class MovieDetailsRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val language: String,
    val nextPage: Int?,
    val lastUpdates: Long
)
