package app.mat.movie.data.remote.dto.common

data class ApiErrorDto(
    val errorCode: Int? = null,
    val statusCode: Int? = null,
    val statusMessage: String? = null
)
