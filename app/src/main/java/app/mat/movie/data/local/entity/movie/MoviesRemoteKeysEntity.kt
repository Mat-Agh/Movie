package app.mat.movie.data.local.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.mat.movie.data.local.type.MovieTypeEntity

@Entity
data class MoviesRemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val language: String,
    val type: MovieTypeEntity,
    val nextPage: Int?,
    val lastUpdated: Long
)
