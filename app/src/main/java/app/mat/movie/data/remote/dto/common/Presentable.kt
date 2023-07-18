package app.mat.movie.data.remote.dto.common

import androidx.compose.runtime.Stable

@Stable
interface Presentable {
    val id: Int
    val title: String
    val posterPath: String?
}