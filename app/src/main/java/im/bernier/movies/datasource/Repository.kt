package im.bernier.movies.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import im.bernier.movies.cast.Person
import im.bernier.movies.credits.Credits
import im.bernier.movies.genre.Genres
import im.bernier.movies.movie.Movie
import im.bernier.movies.movie.Page
import im.bernier.movies.search.SearchResultItem
import io.reactivex.rxjava3.core.Observable
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(val api: Api, val db: AppDatabase) {

    private val creditsLiveData = MutableLiveData<Credits>()
    private val searchLiveData = MutableLiveData<List<SearchResultItem>>()
    private val personLiveData = hashMapOf<Long, MutableLiveData<Person>>()

    fun person(id: Long): LiveData<Person> {
        return personLiveData.getOrPut(id) { MutableLiveData() }
    }

    val credits: LiveData<Credits>
        get() = creditsLiveData

    val searchResult: LiveData<List<SearchResultItem>>
        get() = searchLiveData

    class RequestInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val newUrl = request.url.newBuilder()
                .addQueryParameter("api_key", "a6534fdec1ef0b0d5e392dae172e5a42").build()
            val newRequest = request.newBuilder().url(newUrl).build()
            return chain.proceed(newRequest)
        }
    }

    fun fetchMovie(id: Long): Observable<Movie> {
        return api.getMovie(id)
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

    fun fetchPerson(id: Long) {
        api.getCastById(id).enqueue(object : Callback<Person?> {
            override fun onFailure(call: Call<Person?>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<Person?>, response: retrofit2.Response<Person?>) {
                if (response.isSuccessful) {
                    personLiveData.getOrPut(id, { MutableLiveData() }).postValue(response.body())
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