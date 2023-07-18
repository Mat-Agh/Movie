package app.mat.movie.data.remote.type

import androidx.annotation.DrawableRes
import app.mat.movie.R

sealed class ExternalIdsResource(@DrawableRes val drawableRes: Int) {
    data class Imdb(
        val id: String,
        val type: ExternalContentType
    ) : ExternalIdsResource(
        drawableRes = R.drawable.ic_imdb
    )

    data class Facebook(
        val id: String,
        val type: ExternalContentType
    ) : ExternalIdsResource(
        drawableRes = R.drawable.ic_facebook
    )

    data class Instagram(
        val id: String,
        val type: ExternalContentType
    ) : ExternalIdsResource(
        drawableRes = R.drawable.ic_instagram
    )

    data class Twitter(
        val id: String,
        val type: ExternalContentType
    ) : ExternalIdsResource(
        drawableRes = R.drawable.ic_twitter
    )
}