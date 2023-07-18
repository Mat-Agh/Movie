package app.mat.movie.common.util

import androidx.room.TypeConverter
import app.mat.movie.data.local.type.MovieTypeEntity
import app.mat.movie.data.local.type.ShowTypeEntity
import java.util.Date

class DateConverters {
    @TypeConverter
    fun fromTimestamp(
        value: Long?
    ): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(
        date: Date?
    ): Long? {
        return date?.time
    }
}

class MovieTypeEntityConverters {
    @TypeConverter
    fun toMovieEntityType(
        value: String
    ) = enumValueOf<MovieTypeEntity>(value)

    @TypeConverter
    fun fromMovieTypeEntity(
        value: MovieTypeEntity
    ) = value.name
}

class ShowTypeEntityConverters {
    @TypeConverter
    fun toTvShowEntityType(
        value: String
    ) = enumValueOf<ShowTypeEntity>(value)

    @TypeConverter
    fun fromShowTypeEntity(
        value: ShowTypeEntity
    ) = value.name
}