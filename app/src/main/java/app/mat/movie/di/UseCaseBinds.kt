package app.mat.movie.di

import app.mat.movie.domain.userCase.common.GetCameraAvailableUseCase
import app.mat.movie.domain.userCase.common.GetCameraAvailableUseCaseImpl
import app.mat.movie.domain.userCase.common.GetCombinedCreditsUseCase
import app.mat.movie.domain.userCase.common.GetCombinedCreditsUseCaseImpl
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCaseImpl
import app.mat.movie.domain.userCase.common.GetEpisodeStillsUseCase
import app.mat.movie.domain.userCase.common.GetEpisodeStillsUseCaseImpl
import app.mat.movie.domain.userCase.common.GetFavoritesUseCase
import app.mat.movie.domain.userCase.common.GetFavoritesUseCaseImpl
import app.mat.movie.domain.userCase.common.GetMediaMultiSearchUseCase
import app.mat.movie.domain.userCase.common.GetMediaMultiSearchUseCaseImpl
import app.mat.movie.domain.userCase.common.GetMediaTypeReviewsUseCase
import app.mat.movie.domain.userCase.common.GetMediaTypeReviewsUseCaseImpl
import app.mat.movie.domain.userCase.common.GetPersonDetailsUseCase
import app.mat.movie.domain.userCase.common.GetPersonDetailsUseCaseImpl
import app.mat.movie.domain.userCase.common.GetPersonExternalIdsUseCase
import app.mat.movie.domain.userCase.common.GetPersonExternalIdsUseCaseImpl
import app.mat.movie.domain.userCase.common.GetSpeechToTextAvailableUseCase
import app.mat.movie.domain.userCase.common.GetSpeechToTextAvailableUseCaseImpl
import app.mat.movie.domain.userCase.common.MediaAddSearchQueryUseCase
import app.mat.movie.domain.userCase.common.MediaAddSearchQueryUseCaseImpl
import app.mat.movie.domain.userCase.common.MediaSearchQueriesUseCase
import app.mat.movie.domain.userCase.common.MediaSearchQueriesUseCaseImpl
import app.mat.movie.domain.userCase.common.ScanBitmapForTextUseCase
import app.mat.movie.domain.userCase.common.ScanBitmapForTextUseCaseImpl
import app.mat.movie.domain.userCase.movie.AddRecentlyBrowsedMovieUseCase
import app.mat.movie.domain.userCase.movie.AddRecentlyBrowsedMovieUseCaseImpl
import app.mat.movie.domain.userCase.movie.ClearRecentlyBrowsedMoviesUseCase
import app.mat.movie.domain.userCase.movie.ClearRecentlyBrowsedMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetAllMoviesWatchProvidersUseCase
import app.mat.movie.domain.userCase.movie.GetAllMoviesWatchProvidersUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetDiscoverAllMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetDiscoverAllMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetDiscoverMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetDiscoverMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetFavoriteMoviesIdsUseCase
import app.mat.movie.domain.userCase.movie.GetFavoriteMoviesIdsUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetFavoritesMovieCountUseCase
import app.mat.movie.domain.userCase.movie.GetFavoritesMovieCountUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetFavoritesMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetFavoritesMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMovieBackdropsUseCase
import app.mat.movie.domain.userCase.movie.GetMovieBackdropsUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMovieCollectionUseCase
import app.mat.movie.domain.userCase.movie.GetMovieCollectionUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMovieCreditUseCase
import app.mat.movie.domain.userCase.movie.GetMovieCreditUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMovieDetailsUseCase
import app.mat.movie.domain.userCase.movie.GetMovieDetailsUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMovieExternalIdsUseCase
import app.mat.movie.domain.userCase.movie.GetMovieExternalIdsUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMovieGenresUseCase
import app.mat.movie.domain.userCase.movie.GetMovieGenresUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMovieReviewsCountUseCase
import app.mat.movie.domain.userCase.movie.GetMovieReviewsCountUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMovieVideosUseCase
import app.mat.movie.domain.userCase.movie.GetMovieVideosUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMovieWatchProvidersUseCase
import app.mat.movie.domain.userCase.movie.GetMovieWatchProvidersUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetMoviesOfTypeUseCase
import app.mat.movie.domain.userCase.movie.GetMoviesOfTypeUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetNowPlayingMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetNowPlayingMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetOtherDirectorMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetOtherDirectorMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetPopularMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetPopularMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetRecentlyBrowsedMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetRecentlyBrowsedMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetRelatedMoviesOfTypeUseCase
import app.mat.movie.domain.userCase.movie.GetRelatedMoviesOfTypeUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetTopRatedMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetTopRatedMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetTrendingMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetTrendingMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.GetUpcomingMoviesUseCase
import app.mat.movie.domain.userCase.movie.GetUpcomingMoviesUseCaseImpl
import app.mat.movie.domain.userCase.movie.LikeMovieUseCase
import app.mat.movie.domain.userCase.movie.LikeMovieUseCaseImpl
import app.mat.movie.domain.userCase.movie.UnlikeMovieUseCase
import app.mat.movie.domain.userCase.movie.UnlikeMovieUseCaseImpl
import app.mat.movie.domain.userCase.show.AddRecentlyBrowsedShowUseCase
import app.mat.movie.domain.userCase.show.AddRecentlyBrowsedShowUseCaseImpl
import app.mat.movie.domain.userCase.show.ClearRecentlyBrowsedShowsUseCase
import app.mat.movie.domain.userCase.show.ClearRecentlyBrowsedShowsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetAiringTodayShowsUseCase
import app.mat.movie.domain.userCase.show.GetAiringTodayShowsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetAllShowsWatchProvidersUseCase
import app.mat.movie.domain.userCase.show.GetAllShowsWatchProvidersUseCaseImpl
import app.mat.movie.domain.userCase.show.GetDiscoverAllShowsUseCase
import app.mat.movie.domain.userCase.show.GetDiscoverAllShowsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetDiscoverShowsUseCase
import app.mat.movie.domain.userCase.show.GetDiscoverShowsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetFavoriteShowIdsUseCase
import app.mat.movie.domain.userCase.show.GetFavoriteShowIdsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetFavoriteShowsCountUseCase
import app.mat.movie.domain.userCase.show.GetFavoriteShowsCountUseCaseImpl
import app.mat.movie.domain.userCase.show.GetFavoritesShowsUseCase
import app.mat.movie.domain.userCase.show.GetFavoritesShowsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetNextEpisodeDaysRemainingUseCase
import app.mat.movie.domain.userCase.show.GetNextEpisodeDaysRemainingUseCaseImpl
import app.mat.movie.domain.userCase.show.GetOnTheAirShowsUseCase
import app.mat.movie.domain.userCase.show.GetOnTheAirShowsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetRecentlyBrowsedShowsUseCase
import app.mat.movie.domain.userCase.show.GetRecentlyBrowsedShowsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetRelatedShowsOfTypeUseCase
import app.mat.movie.domain.userCase.show.GetRelatedShowsOfTypeUseCaseImpl
import app.mat.movie.domain.userCase.show.GetSeasonCreditsUseCase
import app.mat.movie.domain.userCase.show.GetSeasonCreditsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetSeasonDetailsUseCase
import app.mat.movie.domain.userCase.show.GetSeasonDetailsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetSeasonsVideosUseCase
import app.mat.movie.domain.userCase.show.GetSeasonsVideosUseCaseImpl
import app.mat.movie.domain.userCase.show.GetShowDetailsUseCase
import app.mat.movie.domain.userCase.show.GetShowDetailsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetShowExternalIdsUseCase
import app.mat.movie.domain.userCase.show.GetShowExternalIdsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetShowGenresUseCase
import app.mat.movie.domain.userCase.show.GetShowGenresUseCaseImpl
import app.mat.movie.domain.userCase.show.GetShowImagesUseCase
import app.mat.movie.domain.userCase.show.GetShowImagesUseCaseImpl
import app.mat.movie.domain.userCase.show.GetShowOfTypeUseCase
import app.mat.movie.domain.userCase.show.GetShowOfTypeUseCaseImpl
import app.mat.movie.domain.userCase.show.GetShowReviewsCountUseCase
import app.mat.movie.domain.userCase.show.GetShowReviewsCountUseCaseImpl
import app.mat.movie.domain.userCase.show.GetShowVideosUseCase
import app.mat.movie.domain.userCase.show.GetShowVideosUseCaseImpl
import app.mat.movie.domain.userCase.show.GetShowWatchProvidersUseCase
import app.mat.movie.domain.userCase.show.GetShowWatchProvidersUseCaseImpl
import app.mat.movie.domain.userCase.show.GetTopRatedShowsUseCase
import app.mat.movie.domain.userCase.show.GetTopRatedShowsUseCaseImpl
import app.mat.movie.domain.userCase.show.GetTrendingShowsUseCase
import app.mat.movie.domain.userCase.show.GetTrendingShowsUseCaseImpl
import app.mat.movie.domain.userCase.show.LikeShowUseCase
import app.mat.movie.domain.userCase.show.LikeShowUseCaseImpl
import app.mat.movie.domain.userCase.show.UnlikeShowUseCase
import app.mat.movie.domain.userCase.show.UnlikeShowUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(
    SingletonComponent::class
)
interface UseCaseBinds {
    @Binds
    fun provideAddRecentlyBrowsedMovieUseCase(
        impl: AddRecentlyBrowsedMovieUseCaseImpl
    ): AddRecentlyBrowsedMovieUseCase

    @Binds
    fun provideClearRecentlyBrowsedMoviesUseCase(
        impl: ClearRecentlyBrowsedMoviesUseCaseImpl
    ): ClearRecentlyBrowsedMoviesUseCase

    @Binds
    fun provideClearRecentlyBrowsedShowsUseCase(
        impl: ClearRecentlyBrowsedShowsUseCaseImpl
    ): ClearRecentlyBrowsedShowsUseCase

    @Binds
    fun provideGetAiringTodayShowUseCase(
        impl: GetAiringTodayShowsUseCaseImpl
    ): GetAiringTodayShowsUseCase

    @Binds
    fun provideGetAllMoviesWatchProvidersUseCase(
        impl: GetAllMoviesWatchProvidersUseCaseImpl
    ): GetAllMoviesWatchProvidersUseCase

    @Binds
    fun provideGetDeviceLanguageUseCase(
        impl: GetDeviceLanguageUseCaseImpl
    ): GetDeviceLanguageUseCase

    @Binds
    fun provideGetDiscoverAllMoviesUseCase(
        impl: GetDiscoverAllMoviesUseCaseImpl
    ): GetDiscoverAllMoviesUseCase

    @Binds
    fun provideGetDiscoverAllTvShowsUseCase(
        impl: GetDiscoverAllShowsUseCaseImpl
    ): GetDiscoverAllShowsUseCase

    @Binds
    fun provideGetDiscoverMoviesUseCase(
        impl: GetDiscoverMoviesUseCaseImpl
    ): GetDiscoverMoviesUseCase

    @Binds
    fun provideGetDiscoverTvShowsUseCase(
        impl: GetDiscoverShowsUseCaseImpl
    ): GetDiscoverShowsUseCase

    @Binds
    fun provideGetEpisodeStillsUseCase(
        impl: GetEpisodeStillsUseCaseImpl
    ): GetEpisodeStillsUseCase

    @Binds
    fun provideGetFavoriteMoviesIdsUseCase(
        impl: GetFavoriteMoviesIdsUseCaseImpl
    ): GetFavoriteMoviesIdsUseCase

    @Binds
    fun provideGetFavoritesMovieCountUseCase(
        impl: GetFavoritesMovieCountUseCaseImpl
    ): GetFavoritesMovieCountUseCase

    @Binds
    fun provideGetFavoritesMoviesUseCase(
        impl: GetFavoritesMoviesUseCaseImpl
    ): GetFavoritesMoviesUseCase

    @Binds
    fun provideGetFavoritesTvShowsUseCase(
        impl: GetFavoritesShowsUseCaseImpl
    ): GetFavoritesShowsUseCase

    @Binds
    fun provideGetFavoritesUseCase(
        impl: GetFavoritesUseCaseImpl
    ): GetFavoritesUseCase

    @Binds
    fun provideGetFavoriteTvShowsCountUseCase(
        impl: GetFavoriteShowsCountUseCaseImpl
    ): GetFavoriteShowsCountUseCase

    @Binds
    fun provideGetMediaMultiSearchUseCase(
        impl: GetMediaMultiSearchUseCaseImpl
    ): GetMediaMultiSearchUseCase

    @Binds
    fun provideGetMediaTypeReviewsUseCase(
        impl: GetMediaTypeReviewsUseCaseImpl
    ): GetMediaTypeReviewsUseCase

    @Binds
    fun provideMovieBackdropsUseCase(
        impl: GetMovieBackdropsUseCaseImpl
    ): GetMovieBackdropsUseCase

    @Binds
    fun provideGetMovieCollectionUseCase(
        impl: GetMovieCollectionUseCaseImpl
    ): GetMovieCollectionUseCase

    @Binds
    fun provideGetMovieCreditsUseCase(
        impl: GetMovieCreditUseCaseImpl
    ): GetMovieCreditUseCase

    @Binds
    fun provideGetMovieDetailsUseCase(
        impl: GetMovieDetailsUseCaseImpl
    ): GetMovieDetailsUseCase

    @Binds
    fun provideGetMovieExternalIdsUseCase(
        impl: GetMovieExternalIdsUseCaseImpl
    ): GetMovieExternalIdsUseCase

    @Binds
    fun provideGetMovieGenresUseCase(
        impl: GetMovieGenresUseCaseImpl
    ): GetMovieGenresUseCase

    @Binds
    fun provideGetMovieReviewsCountUseCase(
        impl: GetMovieReviewsCountUseCaseImpl
    ): GetMovieReviewsCountUseCase

    @Binds
    fun provideGetMoviesOfTypeUseCase(
        impl: GetMoviesOfTypeUseCaseImpl
    ): GetMoviesOfTypeUseCase

    @Binds
    fun provideGetMovieVideosUseCase(
        impl: GetMovieVideosUseCaseImpl
    ): GetMovieVideosUseCase

    @Binds
    fun provideGetMovieWatchProvidersUseCase(
        impl: GetMovieWatchProvidersUseCaseImpl
    ): GetMovieWatchProvidersUseCase

    @Binds
    fun provideGetNowPlayingMoviesUseCase(
        impl: GetNowPlayingMoviesUseCaseImpl
    ): GetNowPlayingMoviesUseCase

    @Binds
    fun provideGetOnTheAirTvShowsUseCase(
        impl: GetOnTheAirShowsUseCaseImpl
    ): GetOnTheAirShowsUseCase

    @Binds
    fun provideGetOtherDirectorMoviesUseCase(
        impl: GetOtherDirectorMoviesUseCaseImpl
    ): GetOtherDirectorMoviesUseCase

    @Binds
    fun provideGetPopularMoviesUseCase(
        impl: GetPopularMoviesUseCaseImpl
    ): GetPopularMoviesUseCase

    @Binds
    fun provideGetRecentlyBrowsedMoviesUseCase(
        impl: GetRecentlyBrowsedMoviesUseCaseImpl
    ): GetRecentlyBrowsedMoviesUseCase

    @Binds
    fun provideGetRecentlyBrowsedShowsUseCase(
        impl: GetRecentlyBrowsedShowsUseCaseImpl
    ): GetRecentlyBrowsedShowsUseCase

    @Binds
    fun provideGetRelatedMoviesOfTypeUseCase(
        impl: GetRelatedMoviesOfTypeUseCaseImpl
    ): GetRelatedMoviesOfTypeUseCase

    @Binds
    fun provideGetRelatedTvShowsOfTypeUseCase(
        impl: GetRelatedShowsOfTypeUseCaseImpl
    ): GetRelatedShowsOfTypeUseCase

    @Binds
    fun provideGetSeasonCreditsUseCase(
        impl: GetSeasonCreditsUseCaseImpl
    ): GetSeasonCreditsUseCase

    @Binds
    fun provideGetSeasonDetailsUseCase(
        impl: GetSeasonDetailsUseCaseImpl
    ): GetSeasonDetailsUseCase

    @Binds
    fun provideGetSeasonVideosUseCase(
        impl: GetSeasonsVideosUseCaseImpl
    ): GetSeasonsVideosUseCase

    @Binds
    fun provideGetSpeechToTextAvailableUseCase(
        impl: GetSpeechToTextAvailableUseCaseImpl
    ): GetSpeechToTextAvailableUseCase

    @Binds
    fun provideCameraAvailableUseCase(
        impl: GetCameraAvailableUseCaseImpl
    ): GetCameraAvailableUseCase

    @Binds
    fun provideGetTopRatedMoviesUseCase(
        impl: GetTopRatedMoviesUseCaseImpl
    ): GetTopRatedMoviesUseCase

    @Binds
    fun provideGetTopRatedTvShowsUseCase(
        impl: GetTopRatedShowsUseCaseImpl
    ): GetTopRatedShowsUseCase

    @Binds
    fun provideGetTrendingMoviesUseCase(
        impl: GetTrendingMoviesUseCaseImpl
    ): GetTrendingMoviesUseCase

    @Binds
    fun provideGetTrendingTvShowsUseCase(
        impl: GetTrendingShowsUseCaseImpl
    ): GetTrendingShowsUseCase

    @Binds
    fun provideGetTvShowGenresUseCase(
        impl: GetShowGenresUseCaseImpl
    ): GetShowGenresUseCase

    @Binds
    fun provideGetTvShowOfTypeUseCase(
        impl: GetShowOfTypeUseCaseImpl
    ): GetShowOfTypeUseCase

    @Binds
    fun provideGetUpcomingMoviesUseCase(
        impl: GetUpcomingMoviesUseCaseImpl
    ): GetUpcomingMoviesUseCase

    @Binds
    fun provideLikeMovieUseCase(
        impl: LikeMovieUseCaseImpl
    ): LikeMovieUseCase

    @Binds
    fun provideMediaAddSearchQueryUseCase(
        impl: MediaAddSearchQueryUseCaseImpl
    ): MediaAddSearchQueryUseCase

    @Binds
    fun provideMediaSearchQueriesUseCase(
        impl: MediaSearchQueriesUseCaseImpl
    ): MediaSearchQueriesUseCase

    @Binds
    fun provideUnlikeMovieUseCase(
        impl: UnlikeMovieUseCaseImpl
    ): UnlikeMovieUseCase

    @Binds
    fun provideGetAllTvShowsWatchProvidersUseCase(
        impl: GetAllShowsWatchProvidersUseCaseImpl
    ): GetAllShowsWatchProvidersUseCase

    @Binds
    fun providesGetPersonDetailsUseCase(
        impl: GetPersonDetailsUseCaseImpl
    ): GetPersonDetailsUseCase

    @Binds
    fun providesGetCombinedCreditsUseCase(
        impl: GetCombinedCreditsUseCaseImpl
    ): GetCombinedCreditsUseCase

    @Binds
    fun providesGetPersonExternalIdsUseCase(
        impl: GetPersonExternalIdsUseCaseImpl
    ): GetPersonExternalIdsUseCase

    @Binds
    fun providesGetTvShowDetailsUseCase(
        impl: GetShowDetailsUseCaseImpl
    ): GetShowDetailsUseCase

    @Binds
    fun providesGetNextEpisodeDaysRemainingUseCase(
        impl: GetNextEpisodeDaysRemainingUseCaseImpl
    ): GetNextEpisodeDaysRemainingUseCase

    @Binds
    fun providesGetTvShowExternalIdsUseCase(
        impl: GetShowExternalIdsUseCaseImpl
    ): GetShowExternalIdsUseCase

    @Binds
    fun providesGetTvShowImagesUseCase(
        impl: GetShowImagesUseCaseImpl
    ): GetShowImagesUseCase

    @Binds
    fun providesGetTvShowReviewsCountUseCase(
        impl: GetShowReviewsCountUseCaseImpl,
    ): GetShowReviewsCountUseCase

    @Binds
    fun providesGetTvShowVideosUseCase(
        impl: GetShowVideosUseCaseImpl
    ): GetShowVideosUseCase

    @Binds
    fun providesGetTvShowWatchProvidersUseCase(
        impl: GetShowWatchProvidersUseCaseImpl
    ): GetShowWatchProvidersUseCase

    @Binds
    fun providesAddRecentlyBrowsedTvShowUseCase(
        impl: AddRecentlyBrowsedShowUseCaseImpl
    ): AddRecentlyBrowsedShowUseCase

    @Binds
    fun providesGetFavoritesTvShowIdsUseCase(
        impl: GetFavoriteShowIdsUseCaseImpl
    ): GetFavoriteShowIdsUseCase

    @Binds
    fun providesLikeTvShowUseCase(
        impl: LikeShowUseCaseImpl
    ): LikeShowUseCase

    @Binds
    fun providesUnlikeTvShowUseCase(
        impl: UnlikeShowUseCaseImpl
    ): UnlikeShowUseCase

    @Binds
    fun providesScanBitmapForTextUseCase(
        impl: ScanBitmapForTextUseCaseImpl
    ): ScanBitmapForTextUseCase
}