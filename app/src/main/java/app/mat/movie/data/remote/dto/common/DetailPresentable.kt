package app.mat.movie.data.remote.dto.common

import androidx.compose.runtime.Stable

@Stable
interface DetailPresentable : Presentable {
    val adult: Boolean?
    val overView: String?
    val backdropPath: String?
    val voteAverage: Float
    val voteCount: Int
}