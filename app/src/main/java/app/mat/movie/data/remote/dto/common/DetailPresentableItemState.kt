package app.mat.movie.data.remote.dto.common

sealed class DetailPresentableItemState {
    object Loading : DetailPresentableItemState()
    object Error : DetailPresentableItemState()
    data class Result(val presentable: DetailPresentable) : DetailPresentableItemState()
}