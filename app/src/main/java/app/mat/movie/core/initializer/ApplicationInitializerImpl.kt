package app.mat.movie.core.initializer

import android.app.Application
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationInitializerImpl @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards ApplicationInitializer>
) {
    fun init(
        application: Application
    ) {
        initializers.forEach { initializer ->
            initializer.init(
                application
            )
        }
    }
}