package app.mat.movie.data.remote.dto.common

sealed class DetailPresentableItemState {
    data object Loading : DetailPresentableItemState()
    data object Error : DetailPresentableItemState()
    data class Result(val presentable: DetailPresentable) : DetailPresentableItemState()
}