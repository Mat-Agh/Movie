package app.mat.movie.data.remote.dto.common

import androidx.compose.runtime.Stable

@Stable
interface MemberDto {
    val id: Int
    val profilePath: String?
    val firstLine: String?
    val secondLine: String?
}