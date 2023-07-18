package app.mat.movie.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import app.mat.movie.common.Parameter
import app.mat.movie.common.util.JsonDateAdapter
import app.mat.movie.data.remote.api.movie.MovieApi
import app.mat.movie.data.remote.api.movie.MovieApiHelper
import app.mat.movie.data.remote.api.movie.MovieApiHelperImpl
import app.mat.movie.data.remote.api.other.OtherApi
import app.mat.movie.data.remote.api.other.OtherApiHelper
import app.mat.movie.data.remote.api.other.OtherApiHelperImpl
import app.mat.movie.data.remote.api.show.ShowApi
import app.mat.movie.data.remote.api.show.ShowApiHelper
import app.mat.movie.data.remote.api.show.ShowApiHelperImpl
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton
import kotlin.time.toJavaDuration

@Module
@InstallIn(
    SingletonComponent::class
)
object NetworkModule {
    @Provides
    @Singleton
    fun provideCache(
        @ApplicationContext context: Context
    ) = Cache(
        context.cacheDir,
        Parameter.Api.cashSize
    )

    @Provides
    @Singleton
    fun provideAuthenticationInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val requestUrl = request.url
        val url = requestUrl.newBuilder()
            .addQueryParameter(
                "api_key",
                Parameter.Api.TMDB_KEY
            )
            .build()

        val modifiedRequest = request.newBuilder().url(
            url
        ).build()

        chain.proceed(
            modifiedRequest
        )
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ): ChuckerInterceptor {
        val collector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(
            context
        ).collector(
            collector
        ).maxContentLength(
            250_000L
        ).redactHeaders(
            "Auth-Token",
            "Bearer"
        ).alwaysReadResponseBody(
            true
        ).build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        cache: Cache,
        authenticationInterceptor: Interceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addInterceptor(
                loggingInterceptor
            )
        }
    }.addInterceptor(
        chuckerInterceptor
    ).addInterceptor(
        authenticationInterceptor
    ).cache(
        cache
    ).connectTimeout(
        Parameter.Api.TimeOut.connect.toJavaDuration()
    ).writeTimeout(
        Parameter.Api.TimeOut.write.toJavaDuration()
    ).readTimeout(
        Parameter.Api.TimeOut.read.toJavaDuration()
    ).build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(
        Date::class.java,
        JsonDateAdapter().nullSafe()
    ).build()

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(
                moshi
            )
        ).baseUrl(
            Parameter.Api.url.toHttpUrl()
        ).client(
            client
        ).build()

    @Singleton
    @Provides
    fun provideTmdbMoviesApi(
        retrofit: Retrofit
    ): MovieApi = retrofit.create(
        MovieApi::class.java
    )

    @Singleton
    @Provides
    fun provideTmdbMoviesApiHelper(
        apiHelper: MovieApiHelperImpl
    ): MovieApiHelper = apiHelper

    @Singleton
    @Provides
    fun provideTmdbTvShowsApi(
        retrofit: Retrofit
    ): ShowApi = retrofit.create(ShowApi::class.java)

    @Singleton
    @Provides
    fun provideTmdbShowsApiHelper(
        apiHelper: ShowApiHelperImpl
    ): ShowApiHelper = apiHelper

    @Singleton
    @Provides
    fun provideTmdbOthersApi(
        retrofit: Retrofit
    ): OtherApi = retrofit.create(
        OtherApi::class.java
    )

    @Singleton
    @Provides
    fun provideTmdbOthersApiHelper(
        apiHelper: OtherApiHelperImpl
    ): OtherApiHelper = apiHelper
}