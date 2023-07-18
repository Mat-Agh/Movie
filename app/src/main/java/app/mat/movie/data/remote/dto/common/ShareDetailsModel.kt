package app.mat.movie.data.remote.dto.common

import app.mat.movie.data.remote.type.ExternalContentType
import app.mat.movie.data.remote.type.ExternalIdsResource

data class ShareDetailsModel(
    val title: String,
    val imdbId: ExternalIdsResource.Imdb
) {
    fun asMessage(): String {
        val contentLink = when (imdbId.type) {
            ExternalContentType.Movie, ExternalContentType.Tv -> "title"
            else -> "name"
        }
        val url = "https://www.imdb.com/$contentLink/${imdbId.id}"

        return "$title\n\n$url"
    }
}
