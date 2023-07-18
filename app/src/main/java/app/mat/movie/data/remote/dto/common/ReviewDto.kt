package app.mat.movie.data.remote.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(
    generateAdapter = true
)
data class ReviewDto(
    val id: String,
    val author: String,
    @Json(name = "author_details")
    val authorDetails: AuthorDetailsDto,
    val content: String,
    @Json(name = "created_at")
    val createdAt: Date?,
    @Json(name = "updated_at")
    val updatedAt: Date?,
    val url: String
)
