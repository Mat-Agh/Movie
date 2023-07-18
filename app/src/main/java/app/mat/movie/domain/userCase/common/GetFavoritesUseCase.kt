package app.mat.movie.domain.userCase.common

import androidx.paging.PagingData
import app.mat.movie.common.type.FavoriteType
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow


interface GetFavoritesUseCase {
    operator fun invoke(
        type: FavoriteType
    ): Flow<PagingData<Presentable>>
}