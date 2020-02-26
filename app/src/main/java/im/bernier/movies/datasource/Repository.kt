package im.bernier.movies.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import im.bernier.movies.cast.Person
import im.bernier.movies.credits.Credits
import im.bernier.movies.genre.Genres
import im.bernier.movies.movie.Movie
import im.bernier.movies.movie.Page
import im.bernier.movies.search.SearchResultItem
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import timber.log.Timber

object Repository {

    lateinit var api: Api
    lateinit var db: AppDatabase

    private val movieLiveData = hashMapOf<Long, MutableLiveData<Movie>>()
    private val creditsLiveData = MutableLiveData<Credits>()
    private val searchLiveData = MutableLiveData<List<SearchResultItem>>()
    private val personLiveData = MutableLiveData<Person>()

    fun movie(id: Long): LiveData<Movie> {
        return movieLiveData.getOrPut(id, { MutableLiveData() })
    }

    val credits: LiveData<Credits>
        get() = creditsLiveData

    val searchResult: LiveData<List<SearchResultItem>>
        get() = searchLiveData

    val person: LiveData<Person>
        get() = personLiveData

    fun init(context: Context) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(RequestInterceptor())
            .build()
        val contentType = "application/json".toMediaType()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            .build()
        api = retrofit.create(Api::class.java)

        db = Room.databaseBuilder(context, AppDatabase::class.java, "movies").build()
    }

    class RequestInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val newUrl = request.url.newBuilder()
                .addQueryParameter("api_key", "a6534fdec1ef0b0d5e392dae172e5a42").build()
            val newRequest = request.newBuilder().url(newUrl).build()
            return chain.proceed(newRequest)
        }
    }

    fun fetchMovie(id: Long) {
        api.getMovie(id).enqueue(object : Callback<Movie?> {
            override fun onFailure(call: Call<Movie?>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<Movie?>, response: retrofit2.Response<Movie?>) {
                if (response.isSuccessful) {
                    movieLiveData.getOrPut(id, { MutableLiveData() }).postValue(response.body())
                }
            }
        })
    }

    fun fetchGenres() {
        api.genres().enqueue(object : Callback<Genres?> {
            override fun onFailure(call: Call<Genres?>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<Genres?>, response: retrofit2.Response<Genres?>) {
                val genres = response.body()
                if (response.isSuccessful && genres != null) {
                    Thread {
                        db.genreDao().insertAll(genres.genres)
                    }.start()
                }
            }
        })
    }

    fun fetchPerson(id: Int) {
        api.getCastById(id).enqueue(object : Callback<Person?> {
            override fun onFailure(call: Call<Person?>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<Person?>, response: retrofit2.Response<Person?>) {
                if (response.isSuccessful) {
                    personLiveData.postValue(response.body())
                }
            }
        })
    }

    fun search(query: String) {
        api.search(query).enqueue(object : Callback<Page<SearchResultItem>?> {
            override fun onFailure(call: Call<Page<SearchResultItem>?>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(
                call: Call<Page<SearchResultItem>?>,
                response: retrofit2.Response<Page<SearchResultItem>?>
            ) {
                response.body()?.let {
                    searchLiveData.postValue(it.results)
                }
            }
        })
    }

}