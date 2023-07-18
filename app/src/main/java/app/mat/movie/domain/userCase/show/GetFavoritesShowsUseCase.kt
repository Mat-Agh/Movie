package app.mat.movie.domain.userCase.show

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.show.ShowFavoriteEntity
import kotlinx.coroutines.flow.Flow

interface GetFavoritesShowsUseCase {
    operator fun invoke(): Flow<PagingData<ShowFavoriteEntity>>
}
