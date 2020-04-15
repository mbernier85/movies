package im.bernier.movies.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import im.bernier.movies.datasource.Api
import im.bernier.movies.datasource.AppDatabase
import im.bernier.movies.datasource.Repository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Michael on 2020-03-14.
 */

@Module
class AppModule(private val application: Application) {

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
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            .build()
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideDb(): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "movies").build()
    }
}