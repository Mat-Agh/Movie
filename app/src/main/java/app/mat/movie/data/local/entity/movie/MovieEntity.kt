package app.mat.movie.data.local.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import app.mat.movie.data.local.type.MovieTypeEntity
import app.mat.movie.data.remote.dto.common.Presentable

@Entity(
    indices = [Index(value = ["type", "language"])]
)
data class MovieEntity(
    override val id: Int,
    val type: MovieTypeEntity,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String?,
    override val title: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    val language: String
) : Presentable {
    @PrimaryKey(autoGenerate = true)
    var entityId: Int = 0
}
