package app.mat.movie.common.type

import androidx.annotation.StringRes
import app.mat.movie.R

sealed class SnackBarEvent(
    @StringRes val messageStringRes: Int
) {
    object NetworkDisconnected : SnackBarEvent(
        R.string.snack_bar_network_disconnected_label
    )

    object NetworkConnected : SnackBarEvent(
        R.string.snack_bar_network_connected_label
    )
}
