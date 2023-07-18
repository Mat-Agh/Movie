package app.mat.movie.common.util

import android.annotation.SuppressLint
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.text.SimpleDateFormat
import java.util.Date

class JsonDateAdapter : JsonAdapter<Date>() {
    private companion object {
        @SuppressLint(
            "SimpleDateFormat"
        )
        val serverDateFormat = SimpleDateFormat(
            "yyyy-MM-dd"
        )

        @SuppressLint(
            "SimpleDateFormat"
        )
        val standardDateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
        )
    }

    override fun fromJson(
        reader: JsonReader
    ): Date? {
        val dateString = reader.nextString()

        if (dateString.isEmpty()) {
            return null
        }

        val serverDateFormat = serverDateFormat.parseOrNull(
            dateString
        )

        val standardDate = standardDateFormat.parseOrNull(
            dateString
        )

        if (standardDate != null) {
            return standardDate
        }

        return null
    }

    override fun toJson(
        writer: JsonWriter,
        value: Date?
    ) {
        if (value != null) {
            writer.value(
                value.toString()
            )
        }
    }
}

private fun SimpleDateFormat.parseOrNull(
    source: String
): Date? {
    return try {
        parse(
            source
        )
    } catch (exception: ArrayIndexOutOfBoundsException) {
        null
    } catch (exception: Exception) {
        null
    }
}