package app.mat.movie.core.initializer

import android.app.Application
import app.mat.movie.domain.configuration.DataSourceConfiguration
import javax.inject.Inject

class ConfigDataSourceInitializer @Inject constructor(
    private val dataSourceConfiguration: DataSourceConfiguration
) : ApplicationInitializer {
    override fun init(
        application: Application
    ) {
        dataSourceConfiguration.init()
    }
}