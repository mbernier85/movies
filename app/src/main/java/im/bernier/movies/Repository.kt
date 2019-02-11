package im.bernier.movies

import android.content.Context
import androidx.room.Room
import im.bernier.movies.genre.Genres
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

object Repository {

    lateinit var api: Api
    lateinit var db: AppDatabase

    fun init(context: Context) {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(RequestInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)

        db = Room.databaseBuilder(context, AppDatabase::class.java, "movies").build()
    }

    class RequestInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val newUrl = request.url().newBuilder().addQueryParameter("api_key", "a6534fdec1ef0b0d5e392dae172e5a42").build()
            val newRequest = request.newBuilder().url(newUrl).build()
            return chain.proceed(newRequest)
        }
    }

    fun fetchGenres() {
        api.genres().enqueue(object: Callback<Genres?> {
            override fun onFailure(call: Call<Genres?>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<Genres?>, response: retrofit2.Response<Genres?>) {
                val genres = response.body()
                if (response.isSuccessful && genres != null) {
                    Thread {
                        db.genreDao().insertAll(genres.genres)
                    }.start()
                } else {
                    Timber.e(response.errorBody().toString())
                }
            }
        })
    }

}