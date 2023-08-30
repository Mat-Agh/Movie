package app.mat.movie.di

import android.content.Context
import app.mat.movie.data.remote.api.movie.MovieApiHelper
import app.mat.movie.data.remote.api.other.OtherApiHelper
import app.mat.movie.data.remote.api.show.ShowApiHelper
import app.mat.movie.data.repository.ConfigRepository
import app.mat.movie.data.repository.FavoritesRepository
import app.mat.movie.data.repository.MovieRepository
import app.mat.movie.data.repository.PersonRepository
import app.mat.movie.data.repository.RecentlyBrowsedRepository
import app.mat.movie.data.repository.SearchRepository
import app.mat.movie.data.repository.SeasonRepository
import app.mat.movie.data.repository.ShowRepository
import app.mat.movie.data.repository.ConfigRepositoryImpl
import app.mat.movie.data.repository.FavoritesRepositoryImpl
import app.mat.movie.data.repository.MovieRepositoryImpl
import app.mat.movie.data.repository.PersonRepositoryImpl
import app.mat.movie.data.repository.RecentlyBrowsedRepositoryImpl
import app.mat.movie.data.repository.SearchRepositoryImpl
import app.mat.movie.data.repository.SeasonRepositoryImpl
import app.mat.movie.data.repository.TvShowRepositoryImpl
import app.mat.movie.domain.configuration.DataSourceConfiguration
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(
    SingletonComponent::class
)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideConfigDataSource(
        @ApplicationContext context: Context,
        externalScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        otherApiHelper: OtherApiHelper,
        movieApiHelper: MovieApiHelper,
        showApiHelper: ShowApiHelper
    ): DataSourceConfiguration = DataSourceConfiguration(
        context = context,
        externalScope = externalScope,
        defaultDispatcher = dispatcher,
        otherApiHelper = otherApiHelper,
        movieApiHelper = movieApiHelper,
        showApiHelper = showApiHelper
    )

    @Module
    @InstallIn(
        SingletonComponent::class
    )
    interface RepositoryBinds {
        @Binds
        @Singleton
        fun provideConfigRepository(
            impl: ConfigRepositoryImpl
        ): ConfigRepository

        @Binds
        @Singleton
        fun bindMovieRepository(
            impl: MovieRepositoryImpl
        ): MovieRepository

        @Binds
        @Singleton
        fun bindTvShowRepository(
            impl: TvShowRepositoryImpl
        ): ShowRepository

        @Binds
        @Singleton
        fun bindsBrowsedRepository(
            impl: RecentlyBrowsedRepositoryImpl
        ): RecentlyBrowsedRepository

        @Binds
        @Singleton
        fun bindFavouritesRepository(
            impl: FavoritesRepositoryImpl
        ): FavoritesRepository

        @Binds
        @Singleton
        fun bindPersonRepository(
            impl: PersonRepositoryImpl
        ): PersonRepository

        @Binds
        @Singleton
        fun bindSearchRepository(
            impl: SearchRepositoryImpl
        ): SearchRepository

        @Binds
        @Singleton
        fun bindSeasonRepository(
            impl: SeasonRepositoryImpl
        ): SeasonRepository
    }
}