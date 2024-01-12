package im.bernier.movies.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import im.bernier.movies.feature.credits.Credits
import im.bernier.movies.feature.genre.Genres
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.movie.Page
import im.bernier.movies.feature.search.SearchResultItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(val api: Api, val db: AppDatabase) {

    private val creditsLiveData = MutableLiveData<Credits>()
    private val searchLiveData = MutableLiveData<List<SearchResultItem>>()

    val credits: LiveData<Credits>
        get() = creditsLiveData

    val searchResult: LiveData<List<SearchResultItem>>
        get() = searchLiveData

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