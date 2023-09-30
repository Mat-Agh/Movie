package app.mat.movie.core.application

import android.app.Application
import app.mat.movie.core.initializer.ApplicationInitializerImpl
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.airbnb.lottie.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MovieApplication : Application(), ImageLoaderFactory {
    //region Variables
    @Inject
    lateinit var initializers: ApplicationInitializerImpl

    @Inject
    lateinit var imageLoader: ImageLoader
    //endregion Variables

    //region Override Methods
    override fun onCreate() {
        super.onCreate()

        setupTimber()

        initializers.init(
            this
        )
    }

    override fun newImageLoader(): ImageLoader = imageLoader
    //endregion Override Methods

    //region Private Methods
    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(
                Timber.DebugTree()
            )
        }
    }
    //endregion Private Methods
}