package app.mat.movie.data.remote.dto.common

import app.mat.movie.common.util.formatted
import java.util.Date

data class DateParam(private val date: Date) {
    override fun toString(): String {
        return date.formatted("yyyy-MM-dd")
    }
}
