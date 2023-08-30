package app.mat.movie.di

import app.mat.movie.domain.useCase.common.GetCameraAvailableUseCase
import app.mat.movie.domain.useCase.common.GetCameraAvailableUseCaseImpl
import app.mat.movie.domain.useCase.common.GetCombinedCreditsUseCase
import app.mat.movie.domain.useCase.common.GetCombinedCreditsUseCaseImpl
import app.mat.movie.domain.useCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.useCase.common.GetDeviceLanguageUseCaseImpl
import app.mat.movie.domain.useCase.common.GetEpisodeStillsUseCase
import app.mat.movie.domain.useCase.common.GetEpisodeStillsUseCaseImpl
import app.mat.movie.domain.useCase.common.GetFavoritesUseCase
import app.mat.movie.domain.useCase.common.GetFavoritesUseCaseImpl
import app.mat.movie.domain.useCase.common.GetMediaMultiSearchUseCase
import app.mat.movie.domain.useCase.common.GetMediaMultiSearchUseCaseImpl
import app.mat.movie.domain.useCase.common.GetMediaTypeReviewsUseCase
import app.mat.movie.domain.useCase.common.GetMediaTypeReviewsUseCaseImpl
import app.mat.movie.domain.useCase.common.GetPersonDetailsUseCase
import app.mat.movie.domain.useCase.common.GetPersonDetailsUseCaseImpl
import app.mat.movie.domain.useCase.common.GetPersonExternalIdsUseCase
import app.mat.movie.domain.useCase.common.GetPersonExternalIdsUseCaseImpl
import app.mat.movie.domain.useCase.common.GetSpeechToTextAvailableUseCase
import app.mat.movie.domain.useCase.common.GetSpeechToTextAvailableUseCaseImpl
import app.mat.movie.domain.useCase.common.MediaAddSearchQueryUseCase
import app.mat.movie.domain.useCase.common.MediaAddSearchQueryUseCaseImpl
import app.mat.movie.domain.useCase.common.MediaSearchQueriesUseCase
import app.mat.movie.domain.useCase.common.MediaSearchQueriesUseCaseImpl
import app.mat.movie.domain.useCase.common.ScanBitmapForTextUseCase
import app.mat.movie.domain.useCase.common.ScanBitmapForTextUseCaseImpl
import app.mat.movie.domain.useCase.movie.AddRecentlyBrowsedMovieUseCase
import app.mat.movie.domain.useCase.movie.AddRecentlyBrowsedMovieUseCaseImpl
import app.mat.movie.domain.useCase.movie.ClearRecentlyBrowsedMoviesUseCase
import app.mat.movie.domain.useCase.movie.ClearRecentlyBrowsedMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetAllMoviesWatchProvidersUseCase
import app.mat.movie.domain.useCase.movie.GetAllMoviesWatchProvidersUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetDiscoverAllMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetDiscoverAllMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetDiscoverMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetDiscoverMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetFavoriteMoviesIdsUseCase
import app.mat.movie.domain.useCase.movie.GetFavoriteMoviesIdsUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetFavoritesMovieCountUseCase
import app.mat.movie.domain.useCase.movie.GetFavoritesMovieCountUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetFavoritesMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetFavoritesMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMovieBackdropsUseCase
import app.mat.movie.domain.useCase.movie.GetMovieBackdropsUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMovieCollectionUseCase
import app.mat.movie.domain.useCase.movie.GetMovieCollectionUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMovieCreditUseCase
import app.mat.movie.domain.useCase.movie.GetMovieCreditUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMovieDetailsUseCase
import app.mat.movie.domain.useCase.movie.GetMovieDetailsUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMovieExternalIdsUseCase
import app.mat.movie.domain.useCase.movie.GetMovieExternalIdsUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMovieGenresUseCase
import app.mat.movie.domain.useCase.movie.GetMovieGenresUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMovieReviewsCountUseCase
import app.mat.movie.domain.useCase.movie.GetMovieReviewsCountUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMovieVideosUseCase
import app.mat.movie.domain.useCase.movie.GetMovieVideosUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMovieWatchProvidersUseCase
import app.mat.movie.domain.useCase.movie.GetMovieWatchProvidersUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetMoviesOfTypeUseCase
import app.mat.movie.domain.useCase.movie.GetMoviesOfTypeUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetNowPlayingMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetNowPlayingMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetOtherDirectorMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetOtherDirectorMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetPopularMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetPopularMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetRecentlyBrowsedMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetRecentlyBrowsedMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetRelatedMoviesOfTypeUseCase
import app.mat.movie.domain.useCase.movie.GetRelatedMoviesOfTypeUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetTopRatedMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetTopRatedMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetTrendingMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetTrendingMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.GetUpcomingMoviesUseCase
import app.mat.movie.domain.useCase.movie.GetUpcomingMoviesUseCaseImpl
import app.mat.movie.domain.useCase.movie.LikeMovieUseCase
import app.mat.movie.domain.useCase.movie.LikeMovieUseCaseImpl
import app.mat.movie.domain.useCase.movie.UnlikeMovieUseCase
import app.mat.movie.domain.useCase.movie.UnlikeMovieUseCaseImpl
import app.mat.movie.domain.useCase.show.AddRecentlyBrowsedShowUseCase
import app.mat.movie.domain.useCase.show.AddRecentlyBrowsedShowUseCaseImpl
import app.mat.movie.domain.useCase.show.ClearRecentlyBrowsedShowsUseCase
import app.mat.movie.domain.useCase.show.ClearRecentlyBrowsedShowsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetAiringTodayShowsUseCase
import app.mat.movie.domain.useCase.show.GetAiringTodayShowsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetAllShowsWatchProvidersUseCase
import app.mat.movie.domain.useCase.show.GetAllShowsWatchProvidersUseCaseImpl
import app.mat.movie.domain.useCase.show.GetDiscoverAllShowsUseCase
import app.mat.movie.domain.useCase.show.GetDiscoverAllShowsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetDiscoverShowsUseCase
import app.mat.movie.domain.useCase.show.GetDiscoverShowsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetFavoriteShowIdsUseCase
import app.mat.movie.domain.useCase.show.GetFavoriteShowIdsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetFavoriteShowsCountUseCase
import app.mat.movie.domain.useCase.show.GetFavoriteShowsCountUseCaseImpl
import app.mat.movie.domain.useCase.show.GetFavoritesShowsUseCase
import app.mat.movie.domain.useCase.show.GetFavoritesShowsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetNextEpisodeDaysRemainingUseCase
import app.mat.movie.domain.useCase.show.GetNextEpisodeDaysRemainingUseCaseImpl
import app.mat.movie.domain.useCase.show.GetOnTheAirShowsUseCase
import app.mat.movie.domain.useCase.show.GetOnTheAirShowsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetRecentlyBrowsedShowsUseCase
import app.mat.movie.domain.useCase.show.GetRecentlyBrowsedShowsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetRelatedShowsOfTypeUseCase
import app.mat.movie.domain.useCase.show.GetRelatedShowsOfTypeUseCaseImpl
import app.mat.movie.domain.useCase.show.GetSeasonCreditsUseCase
import app.mat.movie.domain.useCase.show.GetSeasonCreditsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetSeasonDetailsUseCase
import app.mat.movie.domain.useCase.show.GetSeasonDetailsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetSeasonsVideosUseCase
import app.mat.movie.domain.useCase.show.GetSeasonsVideosUseCaseImpl
import app.mat.movie.domain.useCase.show.GetShowDetailsUseCase
import app.mat.movie.domain.useCase.show.GetShowDetailsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetShowExternalIdsUseCase
import app.mat.movie.domain.useCase.show.GetShowExternalIdsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetShowGenresUseCase
import app.mat.movie.domain.useCase.show.GetShowGenresUseCaseImpl
import app.mat.movie.domain.useCase.show.GetShowImagesUseCase
import app.mat.movie.domain.useCase.show.GetShowImagesUseCaseImpl
import app.mat.movie.domain.useCase.show.GetShowOfTypeUseCase
import app.mat.movie.domain.useCase.show.GetShowOfTypeUseCaseImpl
import app.mat.movie.domain.useCase.show.GetShowReviewsCountUseCase
import app.mat.movie.domain.useCase.show.GetShowReviewsCountUseCaseImpl
import app.mat.movie.domain.useCase.show.GetShowVideosUseCase
import app.mat.movie.domain.useCase.show.GetShowVideosUseCaseImpl
import app.mat.movie.domain.useCase.show.GetShowWatchProvidersUseCase
import app.mat.movie.domain.useCase.show.GetShowWatchProvidersUseCaseImpl
import app.mat.movie.domain.useCase.show.GetTopRatedShowsUseCase
import app.mat.movie.domain.useCase.show.GetTopRatedShowsUseCaseImpl
import app.mat.movie.domain.useCase.show.GetTrendingShowsUseCase
import app.mat.movie.domain.useCase.show.GetTrendingShowsUseCaseImpl
import app.mat.movie.domain.useCase.show.LikeShowUseCase
import app.mat.movie.domain.useCase.show.LikeShowUseCaseImpl
import app.mat.movie.domain.useCase.show.UnlikeShowUseCase
import app.mat.movie.domain.useCase.show.UnlikeShowUseCaseImpl
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