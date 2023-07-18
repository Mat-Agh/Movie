package app.mat.movie.common.type

import androidx.annotation.StringRes
import app.mat.movie.R

enum class FavoriteType {
    Movie,
    TvShow;

    @StringRes
    fun getLabelResourceId() = when (this) {
        Movie -> R.string.favourite_type_movie_label
        TvShow -> R.string.favourite_type_tv_show_label
    }
}