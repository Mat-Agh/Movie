package app.mat.movie.data.remote.dto.common

import app.mat.movie.data.remote.type.MediaType

interface CreditPresentable {
    val id: Int
    val posterPath: String?
    val infoText: String?
    val title: String?
    val mediaType: MediaType
}