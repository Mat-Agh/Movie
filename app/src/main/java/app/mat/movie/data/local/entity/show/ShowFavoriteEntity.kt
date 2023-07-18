package app.mat.movie.data.local.entity.show

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.mat.movie.data.remote.dto.common.Presentable
import java.util.Date

@Entity
data class ShowFavoriteEntity(
    @PrimaryKey
    override val id: Int,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String?,
    val name: String,
    @ColumnInfo(name = "added_date")
    val addedDate: Date
) : Presentable {
    @Transient
    override val title: String = name
}
