package im.bernier.movies.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import im.bernier.movies.BuildConfig
import im.bernier.movies.crypto.CryptographyManager
import im.bernier.movies.crypto.CryptographyManagerImpl
import im.bernier.movies.datasource.remote.Api
import im.bernier.movies.feature.discover.DiscoverRoute
import im.bernier.movies.navigation.Navigator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * Created by Michael on 2020-03-14.
 */

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CryptoModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindsCryptographyManager(impl: CryptographyManagerImpl): CryptographyManager
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppModule {
    @Provides
    @ActivityRetainedScoped
    fun provideNavigator(): Navigator = Navigator(startDestination = DiscoverRoute)

    @Provides
    @ActivityRetainedScoped
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @ActivityRetainedScoped
    fun provideRetrofit(
        client: OkHttpClient,
        json: Json,
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
        }

    @Provides
    @ActivityRetainedScoped
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @ActivityRetainedScoped
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(loggingInterceptor)
                }
            }.addInterceptor(requestInterceptor)
            .build()

    @Provides
    @ActivityRetainedScoped
    fun provideRequestInterceptor(): Interceptor =
        Interceptor {
            val request = it.request()
            val newUrl =
                request.url
                    .newBuilder()
                    .addQueryParameter("api_key", "a6534fdec1ef0b0d5e392dae172e5a42")
                    .build()
            val newRequest = request.newBuilder().url(newUrl).build()
            it.proceed(newRequest)
        }
}
