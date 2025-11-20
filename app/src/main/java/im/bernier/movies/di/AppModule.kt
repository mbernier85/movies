package im.bernier.movies.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import im.bernier.movies.BuildConfig
import im.bernier.movies.datasource.Api
import im.bernier.movies.datasource.AppDatabase
import im.bernier.movies.datasource.CryptographyManager
import im.bernier.movies.datasource.CryptographyManagerImpl
import jakarta.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

/**
 * Created by Michael on 2020-03-14.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class CryptoModule {
    @Binds
    abstract fun bindsCryptographyManager(impl: CryptographyManagerImpl): CryptographyManager
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
        }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
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
    @Singleton
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

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, "movies")
            .fallbackToDestructiveMigration(false)
            .build()
}
