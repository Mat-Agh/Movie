package app.mat.movie.data.remote.dto.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonInfoModel(
    val tvSeriesId: Int,
    val seasonNumber: Int
) : Parcelable
