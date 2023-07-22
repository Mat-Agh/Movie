package app.mat.movie.common.type

sealed class NetworkStatus {
    data object Disconnected : NetworkStatus()
    data object Connected : NetworkStatus()
}