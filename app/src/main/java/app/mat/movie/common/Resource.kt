package app.mat.movie.common

import app.mat.movie.data.remote.dto.common.ApiErrorDto

sealed class Resource<out T> {
    class Loading<T> : Resource<T>()

    class Success<T>(
        val data: T?,
        val isLoading: Boolean = false
    ) : Resource<T>()

    class Error<T>(
        val error: ApiErrorDto
    ) : Resource<T>()

    class Exception<T>(
        val exception: Throwable
    ) : Resource<T>()
}
