package app.mat.movie.data.local.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.mat.movie.data.remote.dto.common.Presentable
import java.util.Date

@Entity
data class MovieFavoriteEntity(
    @PrimaryKey
    override val id: Int,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String?,
    override val title: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "added_date")
    val addedDate: Date
) : Presentable
