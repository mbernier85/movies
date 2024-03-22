package im.bernier.movies.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import im.bernier.movies.feature.account.model.AccountResponse
import im.bernier.movies.feature.authentication.model.SessionRequest
import im.bernier.movies.feature.authentication.model.SessionResponse
import im.bernier.movies.feature.authentication.model.TokenResponse
import im.bernier.movies.feature.authentication.model.ValidateTokenRequest
import im.bernier.movies.feature.authentication.model.ValidateTokenResponse
import im.bernier.movies.feature.genre.Genres
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.movie.Page
import im.bernier.movies.feature.search.SearchResultItem
import im.bernier.movies.feature.tv.TV
import im.bernier.movies.feature.watchlist.AddToWatchListResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    val api: Api,
    val db: AppDatabase,
    private val storage: Storage
) {

    private val searchLiveData = MutableLiveData<List<SearchResultItem>>()

    val searchResult: LiveData<List<SearchResultItem>>
        get() = searchLiveData

    fun validateToken(
        token: String,
        username: String,
        password: String
    ): Single<ValidateTokenResponse> {
        return api.validateToken(ValidateTokenRequest(username, password, token))
            .observeOn(Schedulers.io())
    }

    fun newSession(token: String): Single<SessionResponse> {
        return api.newSession(SessionRequest(token))
            .observeOn(Schedulers.io())
            .doOnSuccess {
                storage.setSessionId(it.session_id)
            }
    }

    fun getAccount(): Single<AccountResponse> {
        return api.getAccount(storage.getSessionId())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                storage.setAccountId(it.id.toString())
            }
    }

    fun login(): Single<TokenResponse> {
        return api.newToken().observeOn(Schedulers.io())
    }

    fun fetchMovie(id: Long): Single<Movie> {
        return api.getMovie(id)
    }

    fun fetchTv(id: Long): Single<TV> {
        return api.getTv(id)
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

    fun loggedIn(): Boolean = storage.getSessionId().isNotEmpty()

    fun addToWatchList(mediaId: Long, watchlist: Boolean, mediaType: String): Single<AddToWatchListResponse> {
        return api.addToWatchlist(
            accountId = storage.getAccountId(),
            sessionId = storage.getSessionId(),
            watchlistRequest = WatchlistRequest(
                media_id = mediaId,
                watchlist = watchlist,
                media_type = mediaType
            )
        ).observeOn(Schedulers.io())
    }
}