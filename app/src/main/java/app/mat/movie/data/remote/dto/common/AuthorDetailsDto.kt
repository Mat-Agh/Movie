package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class AuthorDetailsDto(
    val name: String,
    val username: String,
    @Json(name = "avatar_path")
    val avatarPath: String?,
    val rating: Float?,
    @Transient
    val avatarUrl: String? = null
)
