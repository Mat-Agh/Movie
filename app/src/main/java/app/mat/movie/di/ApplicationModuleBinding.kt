package app.mat.movie.di

import app.mat.movie.core.initializer.ApplicationInitializer
import app.mat.movie.core.initializer.ConfigDataSourceInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(
    SingletonComponent::class
)
abstract class ApplicationModuleBinding {
    @Binds
    @IntoSet
    abstract fun provideApplicationInitializer(
        initializer: ConfigDataSourceInitializer
    ): ApplicationInitializer
}
