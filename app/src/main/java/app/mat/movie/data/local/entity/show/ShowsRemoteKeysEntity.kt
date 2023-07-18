package app.mat.movie.data.local.entity.show

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import app.mat.movie.data.local.type.ShowTypeEntity

@Entity(
    indices = [Index(value = ["language", "type"])]
)
data class ShowsRemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val language: String,
    val type: ShowTypeEntity,
    val nextPage: Int?,
    val lastUpdated: Long
)
