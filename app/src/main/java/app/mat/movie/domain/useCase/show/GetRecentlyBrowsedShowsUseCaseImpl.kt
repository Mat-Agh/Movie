package app.mat.movie.domain.useCase.show

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.show.RecentlyBrowsedShowEntity
import app.mat.movie.data.repository.RecentlyBrowsedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentlyBrowsedShowsUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) : GetRecentlyBrowsedShowsUseCase {
    override operator fun invoke(): Flow<PagingData<RecentlyBrowsedShowEntity>> = recentlyBrowsedRepository.recentlyBrowsedShows()
}