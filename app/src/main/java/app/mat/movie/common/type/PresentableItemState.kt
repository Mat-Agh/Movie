package app.mat.movie.common.type

import app.mat.movie.data.remote.dto.common.Presentable

sealed class PresentableItemState {
    object Loading : PresentableItemState()
    object Error : PresentableItemState()
    data class Result(val presentable: Presentable) : PresentableItemState()
}
