package app.mat.movie.domain.userCase.movie

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.movie.RecentlyBrowsedMovieEntity
import kotlinx.coroutines.flow.Flow

interface GetRecentlyBrowsedMoviesUseCase {
    operator fun invoke(): Flow<PagingData<RecentlyBrowsedMovieEntity>>
}
