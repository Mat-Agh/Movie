package app.mat.movie.domain.useCase.movie

import androidx.paging.PagingData
import androidx.paging.map
import app.mat.movie.common.type.MovieType
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetMoviesOfTypeUseCaseImpl @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCaseImpl,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCaseImpl,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCaseImpl,
    private val getFavoritesMoviesUseCaseImpl: GetFavoritesMoviesUseCaseImpl,
    private val getRecentlyBrowsedMoviesUseCase: GetRecentlyBrowsedMoviesUseCaseImpl,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCaseImpl
) : GetMoviesOfTypeUseCase {
    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    override operator fun invoke(
        type: MovieType,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<Presentable>> {
        return when (type) {
            MovieType.NowPlaying -> getNowPlayingMoviesUseCase(
                deviceLanguage = deviceLanguage,
                filtered = false
            )

            MovieType.TopRated -> getTopRatedMoviesUseCase(
                deviceLanguage = deviceLanguage
            )

            MovieType.Upcoming -> getUpcomingMoviesUseCase(
                deviceLanguage = deviceLanguage
            )

            MovieType.Favorite -> getFavoritesMoviesUseCaseImpl()
            MovieType.RecentlyBrowsed -> getRecentlyBrowsedMoviesUseCase()
            MovieType.Trending -> getTrendingMoviesUseCase(
                deviceLanguage = deviceLanguage
            )
        }.mapLatest { data -> data.map { it } }
    }
}