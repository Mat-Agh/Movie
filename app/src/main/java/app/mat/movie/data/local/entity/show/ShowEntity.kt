package app.mat.movie.data.local.entity.show

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import app.mat.movie.data.local.type.ShowTypeEntity
import app.mat.movie.data.remote.dto.common.Presentable

@Entity(
    indices = [Index(value = ["type", "language"])]
)
data class ShowEntity(
    override val id: Int,
    val type: ShowTypeEntity,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String?,
    override val title: String,
    @ColumnInfo(name = "original_name")
    val originalName: String?,
    val language: String
) : Presentable {
    @PrimaryKey(autoGenerate = true)
    var entityId: Int = 0
}
