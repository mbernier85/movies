package im.bernier.movies.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import im.bernier.movies.datasource.Api
import im.bernier.movies.datasource.AppDatabase
import im.bernier.movies.datasource.Repository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import javax.inject.Singleton

/**
 * Created by Michael on 2020-03-14.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): Api {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(Repository.RequestInterceptor())
            .build()
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "movies").build()
    }
}