package app.mat.movie.domain.useCase.show

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.show.RecentlyBrowsedShowEntity
import kotlinx.coroutines.flow.Flow

interface GetRecentlyBrowsedShowsUseCase {
    operator fun invoke(): Flow<PagingData<RecentlyBrowsedShowEntity>>
}