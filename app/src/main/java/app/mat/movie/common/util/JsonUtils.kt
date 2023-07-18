package app.mat.movie.common.util

import com.google.gson.Gson

fun <T> T.toJsonString(): String = Gson().toJson(
    this
)

fun <T> String.toJsonObject(
    type: Class<T>
): T? = Gson().fromJson(
    this,
    type
)

