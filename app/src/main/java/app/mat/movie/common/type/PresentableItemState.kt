package app.mat.movie.common.type

import app.mat.movie.data.remote.dto.common.Presentable

sealed class PresentableItemState {
    data object Loading : PresentableItemState()
    data object Error : PresentableItemState()
    data class Result(
        val presentable: Presentable
    ) : PresentableItemState()
}
