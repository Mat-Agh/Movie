package app.mat.movie.common.type

sealed class NetworkStatus {
    object Disconnected : NetworkStatus()
    object Connected : NetworkStatus()
}