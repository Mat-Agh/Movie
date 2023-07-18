package app.mat.movie.common

import app.mat.movie.data.remote.dto.common.ApiErrorDto
import com.squareup.moshi.JsonDataException
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse

fun <T> Resource<T>.onSuccess(
    onResult: Resource.Success<T>.() -> Unit
): Resource<T> {
    if (this is Resource.Success) {
        onResult(this)
    }
    return this
}

fun <T> Resource<T>.onError(
    onResult: Resource.Error<T>.() -> Unit
): Resource<T> {
    if (this is Resource.Error<T>) {
        onResult(this)
    }
    return this
}

fun <T> Resource<T>.onException(
    onResult: Resource.Exception<T>.() -> Unit
): Resource<T> {
    if (this is Resource.Exception<T>) {
        onResult(this)
    }
    return this
}

inline fun <T> Call<T>.request(
    crossinline onResult: (response: Resource<T>) -> Unit
) {
    val queueObject = object : Callback<T> {
        override fun onResponse(
            call: Call<T>,
            response: Response<T>
        ) {
            if (response.isSuccessful) {
                onResult(
                    Resource.Success(
                        response.body()
                    )
                )
                return
            }

            val code = response.code()
            val errorBody = response.errorBody()?.toString()

            val message = errorBody?.let { body ->
                try {
                    JSONObject(body).getString("status_message")
                } catch (exception: JSONException) {
                    null
                }
            }

            val statusCode = errorBody?.let { body ->
                try {
                    JSONObject(body).getInt("status_code")
                } catch (exception: JSONException) {
                    null
                }
            }
            val apiError = ApiErrorDto(
                errorCode = code,
                statusMessage = message,
                statusCode = statusCode
            )

            onResult(
                Resource.Error(
                    apiError
                )
            )
        }

        override fun onFailure(
            call: Call<T>,
            throwable: Throwable
        ) {
            onResult(
                Resource.Exception(throwable)
            )
        }
    }
}

suspend fun <T : Any> Call<T>.awaitApiResponse(): Resource<T> {
    return try {
        val response = awaitResponse()

        if (response.isSuccessful) {
            return Resource.Success(
                response.body()
            )
        }

        val code = response.code()
        val errorBody = response.errorBody()?.toString()

        val message = errorBody?.let { body ->
            try {
                JSONObject(body).getString("status_message")
            } catch (exception: JSONException) {
                null
            }
        }

        val statusCode = errorBody?.let { body ->
            try {
                JSONObject(body).getInt("status_code")
            } catch (exception: JSONException) {
                null
            }
        }

        val apiError = ApiErrorDto(
            errorCode = code,
            statusMessage = message,
            statusCode = statusCode
        )

        Resource.Error(
            apiError
        )
    } catch (exception: IOException) {
        Resource.Exception(
            exception
        )
    } catch (exception: HttpException) {
        Resource.Exception(
            exception
        )
    } catch (exception: JsonDataException) {
        Resource.Exception(
            exception
        )
    }
}



