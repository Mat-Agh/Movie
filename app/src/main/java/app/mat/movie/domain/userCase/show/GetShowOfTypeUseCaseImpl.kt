package app.mat.movie.domain.userCase.show

import androidx.paging.PagingData
import androidx.paging.map
import app.mat.movie.common.type.ShowType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetShowOfTypeUseCaseImpl @Inject constructor(
    private val getOnTheAirTvShowsUseCase: GetOnTheAirShowsUseCaseImpl,
    private val getTopRatedTvShowsUseCase: GetTopRatedShowsUseCaseImpl,
    private val getAiringTodayTvShowsUseCase: GetAiringTodayShowsUseCaseImpl,
    private val getTrendingTvShowsUseCase: GetTrendingShowsUseCaseImpl,
    private val getFavouriteTvShowsUseCase: GetFavoritesShowsUseCaseImpl,
    private val getRecentlyBrowsedTvShowsUseCase: GetRecentlyBrowsedShowsUseCaseImpl,
) : GetShowOfTypeUseCase {
    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    override operator fun invoke(
        type: ShowType,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<Presentable>> {
        return when (type) {
            ShowType.OnTheAir -> getOnTheAirTvShowsUseCase(
                deviceLanguage, false
            )

            ShowType.TopRated -> getTopRatedTvShowsUseCase(
                deviceLanguage
            )

            ShowType.AiringToday -> getAiringTodayTvShowsUseCase(
                deviceLanguage
            )

            ShowType.Trending -> getTrendingTvShowsUseCase(
                deviceLanguage
            )

            ShowType.Favorite -> getFavouriteTvShowsUseCase()
            ShowType.RecentlyBrowsed -> getRecentlyBrowsedTvShowsUseCase()
        }.mapLatest { data -> data.map { it } }
    }
}