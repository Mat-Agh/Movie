package app.mat.movie.di

import app.mat.movie.common.util.TextRecognitionHelper
import app.mat.movie.common.util.TextRecognitionHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(
    SingletonComponent::class
)
interface HelperBinds {
    @Binds
    fun provideTextRecognitionHelper(
        impl: TextRecognitionHelperImpl
    ): TextRecognitionHelper
}