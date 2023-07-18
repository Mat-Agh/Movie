package app.mat.movie.common.type

import androidx.annotation.StringRes
import app.mat.movie.R

enum class MovieWatchProviderType {
    Stream,
    Rent,
    Buy;

    @StringRes
    fun getLabelResId() = when (this) {
        Rent -> R.string.movie_provider_type_rent_label
        Buy -> R.string.movie_provider_type_buy_label
        Stream -> R.string.movie_provider_type_stream_label
    }
}