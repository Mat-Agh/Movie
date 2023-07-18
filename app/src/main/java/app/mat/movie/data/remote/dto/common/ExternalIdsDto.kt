package app.mat.movie.data.remote.dto.common

import app.mat.movie.data.remote.type.ExternalContentType
import app.mat.movie.data.remote.type.ExternalIdsResource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class ExternalIdsDto(
    val id: Int,
    @Json(name = "imdb_id")
    val imdbId: String?,

    @Json(name = "facebook_id")
    val facebookId: String?,

    @Json(name = "instagram_id")
    val instagramId: String?,

    @Json(name = "twitter_id")
    val twitterId: String?
) {
    fun toExternalIdList(type: ExternalContentType): List<ExternalIdsResource> {
        return buildList {
            facebookId?.let { id ->
                add(ExternalIdsResource.Facebook(id, type))
            }

            instagramId?.let { id ->
                add(ExternalIdsResource.Instagram(id, type))
            }

            twitterId?.let { id ->
                add(ExternalIdsResource.Twitter(id, type))
            }

            imdbId?.let { id ->
                add(ExternalIdsResource.Imdb(id, type))
            }
        }
    }
}