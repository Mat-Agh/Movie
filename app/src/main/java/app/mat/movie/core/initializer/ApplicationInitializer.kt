package app.mat.movie.core.initializer

import android.app.Application

interface ApplicationInitializer {
    fun init(
        application: Application
    )
}